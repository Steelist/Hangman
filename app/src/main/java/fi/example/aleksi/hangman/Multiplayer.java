package fi.example.aleksi.hangman;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Aleksi on 16.4.2017.
 */

public class Multiplayer extends AppCompatActivity {
    int GUESSES_LEFT = 5;
    TextView toGuess;
    EditText guess;
    private String word = "";
    int wordLength;
    String hidden = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_multiplayer);

        toGuess = (TextView)findViewById(R.id.toguess);
        guess = (EditText)findViewById(R.id.letter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Type a word to guess!");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Multiplayer.this, "Please type something to be guessed!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        builder.show();
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
            Toast.makeText(Multiplayer.this, "You won!", Toast.LENGTH_SHORT).show();
        }

        if (wrongGuess) {
            GUESSES_LEFT--;
        }

        if (GUESSES_LEFT == 0) {
            Toast.makeText(Multiplayer.this, "You lost!", Toast.LENGTH_SHORT).show();
        }
    }
}
