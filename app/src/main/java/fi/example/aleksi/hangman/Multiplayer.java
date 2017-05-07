package fi.example.aleksi.hangman;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
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
    int GUESSES_LEFT;
    TextView toGuess;
    EditText guess;
    String hidden = "";
    private String word = "";
    int wordLength;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_multiplayer);

        GUESSES_LEFT = getResources().getInteger(R.integer.GUESSES_LEFT);
        toGuess = (TextView)findViewById(R.id.toguess);
        guess = (EditText)findViewById(R.id.letter);

        initialDialog();
    }

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
                Toast.makeText(Multiplayer.this, "Maybe next time!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.show();
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
                Toast.makeText(Multiplayer.this, "That's unfortunate!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Multiplayer.this, "Hope you enjoyed!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.show();
    }
}
