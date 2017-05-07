package fi.example.aleksi.hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Aleksi on 23.4.2017.
 */

public class Singleplayer extends AppCompatActivity {
    TextView toGuess;
    EditText guess;
    String hidden = "";
    private String word = "";
    StringDatabase db;
    int wordLength;
    int GUESSES_LEFT;
    int rng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_singleplayer);

        GUESSES_LEFT = getResources().getInteger(R.integer.GUESSES_LEFT);
        guess = (EditText)findViewById(R.id.letter);
        toGuess = (TextView)findViewById(R.id.toguess);
        rng = (int) (5 * Math.random()) + 1;

        databaseFetching();
    }

    public void databaseFetching() {
        db = new StringDatabase(this);

        Cursor cursor = db.getOneString(rng);
        cursor.moveToFirst();
        word = cursor.getString(cursor.getColumnIndex("content")).toUpperCase();
        cursor.close();
        wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            if (word.charAt(i) != ' ') {
                hidden += "_";
            } else {
                hidden += " ";
            }
        }

        toGuess.setText(hidden);
        toGuess.setLetterSpacing((float)0.5);
    }

    public void guessing(View view) {
        boolean wrongGuess = true;

        if (!guess.getText().toString().isEmpty()) {
            String text = guess.getText().toString().toUpperCase();

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

            guess.getText().clear();

            if (hidden.equals(word)) {
                victory();
            }

            if (wrongGuess) {
                GUESSES_LEFT--;
            }

            if (GUESSES_LEFT == 0) {
                defeat();
            }
        }
    }

    public void defeat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("You lost!");

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            recreate();
            }
        });
        builder.setNegativeButton("Main menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Singleplayer.this, "Too bad!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.show();
    }

    public void victory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("You guessed it!");
        builder.setMessage("Secret was: " + toGuess.getText().toString());

        builder.setPositiveButton("Next word", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recreate();
            }
        });
        builder.setNegativeButton("End game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Singleplayer.this, "Sweet victory!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.show();
    }
}
