package fi.example.aleksi.hangman;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Aleksi on 7.5.2017.
 */

public class Settings extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
    }

//    Maybe could do something like this???
//    Tutorial link:
//    https://examples.javacodegeeks.com/android/core/ui/settings/android-settings-example/
//
//    MainActivity.java
//
//    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent modifySettings=new Intent(MainActivity.this,SettingsActivity.class);
//            startActivity(modifySettings);
//        }
//    });
//    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//    StringBuilder builder = new StringBuilder();
//        builder.append("\n" + "Perform Sync:\t" + sharedPrefs.getBoolean("perform_sync", false));
//        builder.append("\n" + "Sync Intervals:\t" + sharedPrefs.getString("sync_interval", "-1"));
//        builder.append("\n" + "Name:\t" + sharedPrefs.getString("full_name", "Not known to us"));
//        builder.append("\n" + "Email Address:\t" + sharedPrefs.getString("email_address", "No EMail Address Provided"));
//        builder.append("\n" + "Customized Notification Ringtone:\t" + sharedPrefs.getString("notification_ringtone", ""));
//        builder.append("\n\nClick on Settings Button at bottom right corner to Modify Your Prefrences");
//    TextView settingsTextView = (TextView) findViewById(R.id.settingsContent);
//        settingsTextView.setText(builder.toString());

//    SettingsActivity.java
//
//    package com.javacodegeeks.examples.rivu.chakraborty.androidsettingsexample;
//
//        import android.preference.PreferenceActivity;
//        import android.os.Bundle;
//    public class SettingsActivity extends PreferenceActivity {
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.preferences);
//        }
//    }

//    ActivityMain
//
//    <?xml version="1.0" encoding="utf-8"?>
//    <android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
//        xmlns:app="http://schemas.android.com/apk/res-auto"
//        xmlns:tools="http://schemas.android.com/tools"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:fitsSystemWindows="true">
//
//        <android.support.design.widget.AppBarLayout
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:theme="@style/AppTheme.AppBarOverlay">
//
//        <android.support.v7.widget.Toolbar
//        android:id="@+id/toolbar"
//        android:layout_width="match_parent"
//        android:layout_height="?attr/actionBarSize"
//        android:background="?attr/colorPrimary"
//        app:popupTheme="@style/AppTheme.PopupOverlay" />
//
//        </android.support.design.widget.AppBarLayout>
//
//        <include layout="@layout/content_settings" />
//
//        <android.support.design.widget.FloatingActionButton
//        android:id="@+id/fab"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:layout_gravity="bottom|end"
//        android:layout_margin="@dimen/fab_margin"
//        android:src="@android:drawable/ic_menu_preferences" />
//    </android.support.design.widget.CoordinatorLayout>

}
