package fi.example.aleksi.hangman;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    int GUESSES_LEFT = 5;
    int rng;
    int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_singleplayer);

        guess = (EditText)findViewById(R.id.letter);
        toGuess = (TextView)findViewById(R.id.toguess);
        rng = (int) (5 * Math.random()) + 1;

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
    }

    public void guessing(View view) {
        boolean wrongGuess = true;
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

        guess.setText("");

        if (hidden.equals(word)) {
            Toast.makeText(Singleplayer.this, "You won!", Toast.LENGTH_SHORT).show();
        }

        if (wrongGuess) {
            GUESSES_LEFT--;
        }

        if (GUESSES_LEFT == 0) {
            Toast.makeText(Singleplayer.this, "You lost!", Toast.LENGTH_SHORT).show();
        }
    }
}
