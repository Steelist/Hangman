package fi.example.aleksi.hangman;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Aleksi Hella on 23.4.2017.
 */

public class Singleplayer extends AppCompatActivity {
    TextView toGuess;
    TextView guessedLetters;
    EditText guess;
    ImageView fault1;
    ImageView fault2;
    ImageView fault3;
    ImageView fault4;
    ImageView fault5;
    ImageView fault6;

    String hidden;
    String word;
    String categoryName;
    int wordLength;
    int GUESSES_LEFT;
    int score;
    boolean SHAKE_REVEAL;
    boolean shakeUsed;
    SharedPreferences sp;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    SoundPool soundPool;
    AudioManager audioManager;
    float actualVolume;
    float maxVolume;
    float volume;
    int correct;
    int incorrect;

    /*
    Creates the activity, sets the view to its layout and disables the top bar.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_singleplayer);

        initializeValues();
        initialDialog();

        if (SHAKE_REVEAL) {
            initializeShakeDetector();
        }
    }

    /*
    Initializes the values used in the activity.
     */
    public void initializeValues() {
        fault1 = (ImageView)findViewById(R.id.fault1);
        fault2 = (ImageView)findViewById(R.id.fault2);
        fault3 = (ImageView)findViewById(R.id.fault3);
        fault4 = (ImageView)findViewById(R.id.fault4);
        fault5 = (ImageView)findViewById(R.id.fault5);
        fault6 = (ImageView)findViewById(R.id.fault6);
        hideMarks();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        guess = (EditText)findViewById(R.id.letter);
        toGuess = (TextView)findViewById(R.id.toguess);
        guessedLetters = (TextView)findViewById(R.id.guessedLetters);
        GUESSES_LEFT = Integer.parseInt(sp.getString("guesses", "-1"));
        SHAKE_REVEAL = sp.getBoolean("helper", false);
        shakeUsed = false;
        hidden = "";
        word = "";
        score = 0;

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume / maxVolume;

        soundPool = new SoundPool.Builder().setMaxStreams(10).build();
        correct = soundPool.load(this, R.raw.correct, 1);
        incorrect = soundPool.load(this, R.raw.incorrect, 1);
    }

    /*
    Removes all visible marks temporarily.
     */
    public void hideMarks() {
        fault1.setVisibility(View.GONE);
        fault2.setVisibility(View.GONE);
        fault3.setVisibility(View.GONE);
        fault4.setVisibility(View.GONE);
        fault5.setVisibility(View.GONE);
        fault6.setVisibility(View.GONE);
    }

    /*
    Creates the initial dialog in singleplayer which asks for player to choose from categories.
     */
    public void initialDialog() {
        AlertDialog.Builder builderOuter = new AlertDialog.Builder(this);
        builderOuter.setCancelable(false);
        builderOuter.setTitle("Select a category!");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Fruits");
        arrayAdapter.add("Games");
        arrayAdapter.add("Movies");
        arrayAdapter.add("Animals");
        arrayAdapter.add("Professions");

        builderOuter.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builderOuter.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                categoryName = arrayAdapter.getItem(which).toLowerCase();
                AlertDialog.Builder builderInner = new AlertDialog.Builder(Singleplayer.this);
                builderInner.setCancelable(false);
                builderInner.setTitle("You selected");
                builderInner.setMessage("Hidden words from " + categoryName);
                builderInner.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        databaseFetching();
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderOuter.show();
    }

    /*
    Fetches a random word from SQLite database using cursor. Places the random word in variable word.
    Also sets letter spacing.
     */
    public void databaseFetching() {
        // Updated for each new input set in the database
        int amountOfInputsInDatabase = 40;
        int rng = (int) (amountOfInputsInDatabase * Math.random()) + 1;
        StringDatabase db = new StringDatabase(this);

        Cursor cursor = db.getOneString(rng, categoryName);
        cursor.moveToFirst();
        word = cursor.getString(cursor.getColumnIndex("content")).toUpperCase();
        cursor.close();
        wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            if (word.charAt(i) == ' ') {
                hidden += " ";
            } else if (word.charAt(i) == '-') {
                hidden += "-";
            } else if (word.charAt(i) == '\'') {
                hidden += "'";
            } else {
                hidden += "_";
            }
        }

        toGuess.setText(hidden);
        toGuess.setLetterSpacing((float)0.5);
    }

    /*
    Initializes custom class ShakeDetector and phone's accelerometer to be ready to detect shaking.
     */
    public void initializeShakeDetector() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                handleShakeEvent(count);
            }
        });
    }

    /*
    Handles what happens after a wanted shake has occurred.
     */
    public void handleShakeEvent(int count) {
        int randomLetterIndex = (int) (wordLength * Math.random());

        while (hidden.charAt(randomLetterIndex) == word.charAt(randomLetterIndex)) {
            randomLetterIndex = (int) (wordLength * Math.random());
        }

        if (count == 2 && !shakeUsed) {
            if (randomLetterIndex == wordLength - 1) {
                hidden = hidden.substring(0, randomLetterIndex) + word.charAt(randomLetterIndex);
                toGuess.setText(hidden);
            } else if (randomLetterIndex < wordLength) {
                hidden = hidden.substring(0, randomLetterIndex) + word.charAt(randomLetterIndex)
                        + hidden.substring(randomLetterIndex + 1, wordLength);
                toGuess.setText(hidden);
            }

            shakeUsed = true;
        } else if (shakeUsed) {
            Toast.makeText(Singleplayer.this, "Shaker help used already!", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    Handles the core game logic.
     */
    public void guessing(View view) {
        View v = this.getCurrentFocus();
        boolean wrongGuess = true;
        boolean sameWrongLetter = false;

        if (!guess.getText().toString().isEmpty()) {
            String text = guess.getText().toString().toUpperCase();
            String[] wrongGuesses = guessedLetters.getText().toString().split(" ");

            for (int i = 0; i < wordLength; i++) {
                if (i == wordLength - 1 &&
                        text.charAt(0) == word.charAt(i)) {
                    hidden = hidden.substring(0, i) + text;
                    toGuess.setText(hidden);
                    wrongGuess = false;
                } else if (i < wordLength &&
                        text.charAt(0) == word.charAt(i)) {
                    hidden = hidden.substring(0, i) + text + hidden.substring(i + 1, wordLength);
                    toGuess.setText(hidden);
                    wrongGuess = false;
                }
            }

            if (hidden.equals(word)) {
                victory();
            }

            for (int i = 0; i < wrongGuesses.length; i++) {
                if (text.equals(wrongGuesses[i])) {
                    sameWrongLetter = true;
                }
            }

            if (wrongGuess && !sameWrongLetter) {
                String tmp = guessedLetters.getText().toString();
                guessedLetters.setText(tmp + " " + text);
                GUESSES_LEFT--;

                setVisibleAndAnimate();
                soundPool.play(incorrect, volume, volume, 1, 0, 1f);

                if (GUESSES_LEFT == 0) {
                    defeat();
                }
            } else if (!sameWrongLetter) {
                soundPool.play(correct, volume, volume, 1, 0, 1f);
            }

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            guess.getText().clear();
        }
    }

    /*
    Sets a mark visible and animates it.
     */
    public void setVisibleAndAnimate() {
        switch(GUESSES_LEFT) {
            case 0:
                fault6.setVisibility(View.VISIBLE);
                markAnimation(fault6);
                break;
            case 5:
                fault5.setVisibility(View.VISIBLE);
                markAnimation(fault5);
                break;
            case 4:
                fault4.setVisibility(View.VISIBLE);
                markAnimation(fault4);
                break;
            case 3:
                fault3.setVisibility(View.VISIBLE);
                markAnimation(fault3);
                break;
            case 2:
                fault2.setVisibility(View.VISIBLE);
                markAnimation(fault2);
                break;
            case 1:
                fault1.setVisibility(View.VISIBLE);
                markAnimation(fault1);
                break;
        }
    }

    /*
    Handles what happens when player loses.
     */
    public void defeat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Your guessing is over!");
        builder.setMessage("You got this many right " + hidden + " with a score of " + score);

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countPositionForHighscore();
                recreate();
            }
        });
        builder.setNegativeButton("Main menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countPositionForHighscore();
                finish();
            }
        });

        builder.show();
    }

    /*
    Handles what happens when player wins.
     */
    public void victory() {
        score++;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("You guessed it!");
        builder.setMessage("Secret was " + word + " and you currently have " + score + " points");

        builder.setPositiveButton("Next word", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GUESSES_LEFT = Integer.parseInt(sp.getString("guesses", "-1"));
                hidden = "";
                word = "";
                guessedLetters.setText("");
                shakeUsed = false;
                hideMarks();
                databaseFetching();
            }
        });
        builder.setNegativeButton("End game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countPositionForHighscore();
                Toast.makeText(Singleplayer.this, "Sweet victory!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.show();
    }

    /*
    Checks highscore position via shared preferences.
     */
    public void countPositionForHighscore() {
        sp = getSharedPreferences("highscores",
                MODE_PRIVATE);
        int first = sp.getInt("first", 0);
        int second = sp.getInt("second", 0);
        int third = sp.getInt("third", 0);
        int tmpFirst;
        int tmpSecond;
        SharedPreferences.Editor editor = getSharedPreferences("highscores",
                MODE_PRIVATE).edit();

        if (score >= first) {
            tmpFirst = first;
            tmpSecond = second;

            editor.putInt("first", score);
            editor.putInt("second", tmpFirst);
            editor.putInt("third", tmpSecond);
            editor.commit();
        } else if (score >= second) {
            tmpSecond = second;

            editor.putInt("second", score);
            editor.putInt("third", tmpSecond);
            editor.commit();
        } else if (score >= third) {
            editor.putInt("third", score);
            editor.commit();
        }
    }

    /*
    Guides how an image view should be animated and then starts it.
     */
    public void markAnimation(ImageView mark) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mark, "rotation", 0, -1080);
        anim.setDuration(1500);
        anim.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator scale = ObjectAnimator.ofFloat(mark, "scaleX", 0, 1);
        scale.setDuration(1500);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(anim);
        set.playTogether(scale);
        set.start();
    }

    /*
    Registers the session manager listener once the activity has resumed.
     */
    @Override
    public void onResume() {
        super.onResume();

        if (SHAKE_REVEAL) {
            mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
        }
    }

    /*
    Unregisters the sensor manager once the activity has paused (another activity comes into foreground).
     */
    @Override
    public void onPause() {
        if (SHAKE_REVEAL) {
            mSensorManager.unregisterListener(mShakeDetector);
        }

        super.onPause();
    }

    /*
    Unregisters the sensor manager once the activity has been destroyed.
     */
    @Override
    protected void onDestroy() {
        if (SHAKE_REVEAL) {
            mSensorManager.unregisterListener(mShakeDetector);
        }

        super.onDestroy();
    }
}
