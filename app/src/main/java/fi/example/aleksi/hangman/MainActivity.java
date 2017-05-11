package fi.example.aleksi.hangman;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Aleksi Hella on 23.4.2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    /*
    Starts a multiplayer game.
     */
    public void startMultiplayerGame(View view) {
        startActivity(new Intent(this, Multiplayer.class));
    }

    /*
    Starts a single player game.
     */
    public void startSingleplayerGame(View view) {
        startActivity(new Intent(this, Singleplayer.class));
    }

    /*
    Starts activity that will take user to the highscore view.
     */
    public void startHighscore(View v) {
        startActivity(new Intent(this, Highscore.class));
    }

    /*
    Starts activity that will take user to the settings view.
     */
    public void startSettings(View v) {
        startActivity(new Intent(this, Settings.class));
    }
}
