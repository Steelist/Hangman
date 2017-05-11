package fi.example.aleksi.hangman;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Aleksi Hella on 16.4.2017.
 */

public class Multiplayer extends AppCompatActivity {
    TextView toGuess;
    TextView guessedLetters;
    EditText guess;
    ImageView fault1;
    ImageView fault2;
    ImageView fault3;
    ImageView fault4;
    ImageView fault5;
    ImageView fault6;

    String hidden = "";
    String word = "";
    int wordLength;
    int GUESSES_LEFT;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_multiplayer);

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
        toGuess = (TextView)findViewById(R.id.toguess);
        guessedLetters = (TextView)findViewById(R.id.guessedLetters);
        guess = (EditText)findViewById(R.id.letter);
        GUESSES_LEFT = Integer.parseInt(sp.getString("guesses", "-1"));
        SHAKE_REVEAL = sp.getBoolean("helper", false);
        shakeUsed = false;

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
    Gives user the initial dialog allowing a secret to be inputted for guessing.
     */
    public void initialDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Type a secret to guess!");
        builder.setMessage("(Length between 3 and 16)");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(16)});

        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (input.getText().toString().length() >= 3) {
                    word = input.getText().toString().toUpperCase();
                    wordLength = word.length();

                    for (int i = 0; i < wordLength; i++) {
                        if (word.charAt(i) != ' ') {
                            hidden += "_";
                        } else {
                            hidden += " ";
                        }
                    }

                    toGuess.setText(hidden);
                    toGuess.setLetterSpacing((float) 0.5);
                } else {
                    Toast.makeText(Multiplayer.this, "More than 3!", Toast.LENGTH_LONG).show();
                    recreate();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.show();
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
            Toast.makeText(Multiplayer.this, "Shaker help used already!", Toast.LENGTH_SHORT).show();
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
                guessedLetters.setText(tmp + " " + guess.getText().toString().toUpperCase());
                GUESSES_LEFT--;

                setVisibleAndAnimate();
                soundPool.play(incorrect, volume, volume, 1, 0, 1f);

                if (GUESSES_LEFT == 0) {
                    defeat();
                }
            } else if (!sameWrongLetter){
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
        builder.setTitle("You were defeated!");
        builder.setMessage("You guessed " + hidden + " of the secret " + word);

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recreate();
            }
        });
        builder.setNegativeButton("Main menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.show();
    }

    /*
    Handles what happens when player wins.
     */
    public void victory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("You guessed it!");
        builder.setMessage("Secret was " + toGuess.getText().toString());

        builder.setPositiveButton("Next word", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Multiplayer.this, "New challenge!", Toast.LENGTH_SHORT).show();
                recreate();
            }
        });
        builder.setNegativeButton("End game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.show();
    }

    /*
    Guides how an image view should be animated and then starts it.
     */
    public void markAnimation(ImageView mark) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mark, "rotation", 0, -2160);
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
