package fi.example.aleksi.hangman;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Aleksi Hella on 7.5.2017.
 */

public class Highscore extends AppCompatActivity {
    SharedPreferences prefs;
    TextView firstPlace;
    TextView secondPlace;
    TextView thirdPlace;
    TextView playername;
    int first;
    int second;
    int third;
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_highscore);

        firstPlace = (TextView)findViewById(R.id.firstPlace);
        secondPlace = (TextView)findViewById(R.id.secondPlace);
        thirdPlace = (TextView)findViewById(R.id.thirdPlace);
        playername = (TextView)findViewById(R.id.playername);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        name = prefs.getString("username", "Nameless");

        fetchHighscores();
    }

    /*
    Fetches the highscores from shared preferences and sets them in their places.
     */
    public void fetchHighscores() {
        prefs = getSharedPreferences("highscores",
                MODE_PRIVATE);
        first = prefs.getInt("first", 0);
        second = prefs.getInt("second", 0);
        third = prefs.getInt("third", 0);

        firstPlace.setText("1st: " + Integer.toString(first) + " points");
        secondPlace.setText("2nd: " + Integer.toString(second) + " points");
        thirdPlace.setText("3rd: " + Integer.toString(third) + " points");
        playername.setText("By: " + name);
    }
}
