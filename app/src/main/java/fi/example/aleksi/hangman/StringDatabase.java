package fi.example.aleksi.hangman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aleksi on 23.4.2017.
 */

public class StringDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "String.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "strings";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CONTENT = "content";

    public StringDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CONTENT + " TEXT)"
        );

        insertStrings(db, "Centipede");
        insertStrings(db, "Brightness");
        insertStrings(db, "Ubiquitous");
        insertStrings(db, "Omnipotent");
        insertStrings(db, "Horrendous");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insertStrings(SQLiteDatabase db, String content) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CONTENT, content);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getOneString(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }
}
