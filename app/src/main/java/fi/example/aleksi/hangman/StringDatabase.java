package fi.example.aleksi.hangman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aleksi Hella on 23.4.2017.
 */

public class StringDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "String.db";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String TABLE_FRUITS = "fruits";
    public static final String TABLE_GAMES = "games";
    public static final String TABLE_MOVIES = "movies";
    public static final String TABLE_ANIMALS = "animals";
    public static final String TABLE_PROFESSIONS = "professions";

    /*
    Initializes database.
     */
    public StringDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
    Executes query that creates tables and inserts data into tables.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_FRUITS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CONTENT + " TEXT)"
        );

        db.execSQL("CREATE TABLE " + TABLE_GAMES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CONTENT + " TEXT)"
        );

        db.execSQL("CREATE TABLE " + TABLE_MOVIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CONTENT + " TEXT)"
        );

        db.execSQL("CREATE TABLE " + TABLE_ANIMALS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CONTENT + " TEXT)"
        );

        db.execSQL("CREATE TABLE " + TABLE_PROFESSIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_CONTENT + " TEXT)"
        );

        insertFruits(db);
        insertGames(db);
        insertMovies(db);
        insertAnimals(db);
        insertProfessions(db);
    }

    /*
    Inserts data into fruits table.
     */
    public void insertFruits(SQLiteDatabase db) {
        insertStrings(db, "Watermelon", TABLE_FRUITS);
        insertStrings(db, "Tangerine", TABLE_FRUITS);
        insertStrings(db, "Strawberry", TABLE_FRUITS);
        insertStrings(db, "Pomelo", TABLE_FRUITS);
        insertStrings(db, "Plum", TABLE_FRUITS);
        insertStrings(db, "Pomegranate", TABLE_FRUITS);
        insertStrings(db, "Banana", TABLE_FRUITS);
        insertStrings(db, "Pineapple", TABLE_FRUITS);
        insertStrings(db, "Apple", TABLE_FRUITS);
        insertStrings(db, "Pear", TABLE_FRUITS);

        insertStrings(db, "Orange", TABLE_FRUITS);
        insertStrings(db, "Peach", TABLE_FRUITS);
        insertStrings(db, "Passion fruit", TABLE_FRUITS);
        insertStrings(db, "Persimmon", TABLE_FRUITS);
        insertStrings(db, "Nectarine", TABLE_FRUITS);
        insertStrings(db, "Mulberry", TABLE_FRUITS);
        insertStrings(db, "Quince", TABLE_FRUITS);
        insertStrings(db, "Raspberry", TABLE_FRUITS);
        insertStrings(db, "Mango", TABLE_FRUITS);
        insertStrings(db, "Lemon", TABLE_FRUITS);

        insertStrings(db, "Lime", TABLE_FRUITS);
        insertStrings(db, "Kiwi", TABLE_FRUITS);
        insertStrings(db, "Guava", TABLE_FRUITS);
        insertStrings(db, "Grapefruit", TABLE_FRUITS);
        insertStrings(db, "Elderberry", TABLE_FRUITS);
        insertStrings(db, "Date", TABLE_FRUITS);
        insertStrings(db, "Coconut", TABLE_FRUITS);
        insertStrings(db, "Carambola", TABLE_FRUITS);
        insertStrings(db, "Pitaya", TABLE_FRUITS);
        insertStrings(db, "Avocado", TABLE_FRUITS);

        insertStrings(db, "Apricot", TABLE_FRUITS);
        insertStrings(db, "Cherry", TABLE_FRUITS);
        insertStrings(db, "Fig", TABLE_FRUITS);
        insertStrings(db, "Grape", TABLE_FRUITS);
        insertStrings(db, "Miracle fruit", TABLE_FRUITS);
        insertStrings(db, "Tamarillo", TABLE_FRUITS);
        insertStrings(db, "Durian", TABLE_FRUITS);
        insertStrings(db, "Feijoa", TABLE_FRUITS);
        insertStrings(db, "Pitanga", TABLE_FRUITS);
        insertStrings(db, "Soursop", TABLE_FRUITS);
    }

    /*
    Inserts data into games table.
     */
    public void insertGames(SQLiteDatabase db) {
        insertStrings(db, "League of legends", TABLE_GAMES);
        insertStrings(db, "Half-life", TABLE_GAMES);
        insertStrings(db, "Grand theft auto", TABLE_GAMES);
        insertStrings(db, "Bioshock", TABLE_GAMES);
        insertStrings(db, "Mass effect", TABLE_GAMES);
        insertStrings(db, "Tomb raider", TABLE_GAMES);
        insertStrings(db, "Battlefield", TABLE_GAMES);
        insertStrings(db, "Starcraft", TABLE_GAMES);
        insertStrings(db, "World of warcraft", TABLE_GAMES);
        insertStrings(db, "Overwatch", TABLE_GAMES);

        insertStrings(db, "Diablo", TABLE_GAMES);
        insertStrings(db, "The witcher", TABLE_GAMES);
        insertStrings(db, "Minecraft", TABLE_GAMES);
        insertStrings(db, "The sims", TABLE_GAMES);
        insertStrings(db, "Team fortress", TABLE_GAMES);
        insertStrings(db, "Splinter cell", TABLE_GAMES);
        insertStrings(db, "Assassin's creed", TABLE_GAMES);
        insertStrings(db, "Undertale", TABLE_GAMES);
        insertStrings(db, "Dark souls", TABLE_GAMES);
        insertStrings(db, "Bloodborne", TABLE_GAMES);

        insertStrings(db, "Age of empires", TABLE_GAMES);
        insertStrings(db, "Crysis", TABLE_GAMES);
        insertStrings(db, "Fallout", TABLE_GAMES);
        insertStrings(db, "Halo", TABLE_GAMES);
        insertStrings(db, "Call of duty", TABLE_GAMES);
        insertStrings(db, "Spore", TABLE_GAMES);
        insertStrings(db, "Deus ex", TABLE_GAMES);
        insertStrings(db, "Max payne", TABLE_GAMES);
        insertStrings(db, "Bayonetta", TABLE_GAMES);
        insertStrings(db, "Dota", TABLE_GAMES);

        insertStrings(db, "Doom", TABLE_GAMES);
        insertStrings(db, "Trials", TABLE_GAMES);
        insertStrings(db, "Ninja gaiden", TABLE_GAMES);
        insertStrings(db, "Portal", TABLE_GAMES);
        insertStrings(db, "The legend of zelda", TABLE_GAMES);
        insertStrings(db, "Destiny", TABLE_GAMES);
        insertStrings(db, "Red dead redemption", TABLE_GAMES);
        insertStrings(db, "Super mario bros", TABLE_GAMES);
        insertStrings(db, "Borderlands", TABLE_GAMES);
        insertStrings(db, "Tetris", TABLE_GAMES);
    }

    /*
    Inserts data into movies table.
     */
    public void insertMovies(SQLiteDatabase db) {
        insertStrings(db, "The godfather", TABLE_MOVIES);
        insertStrings(db, "The shawshank redemption", TABLE_MOVIES);
        insertStrings(db, "Pulp fiction", TABLE_MOVIES);
        insertStrings(db, "Star wars", TABLE_MOVIES);
        insertStrings(db, "The lord of the rings", TABLE_MOVIES);
        insertStrings(db, "Forrest gump", TABLE_MOVIES);
        insertStrings(db, "The dark knight", TABLE_MOVIES);
        insertStrings(db, "The matrix", TABLE_MOVIES);
        insertStrings(db, "Fight club", TABLE_MOVIES);
        insertStrings(db, "Indiana jones", TABLE_MOVIES);

        insertStrings(db, "Schindler's list", TABLE_MOVIES);
        insertStrings(db, "Saving private ryan", TABLE_MOVIES);
        insertStrings(db, "Gladiator", TABLE_MOVIES);
        insertStrings(db, "Star trek", TABLE_MOVIES);
        insertStrings(db, "Braveheart", TABLE_MOVIES);
        insertStrings(db, "The shining", TABLE_MOVIES);
        insertStrings(db, "Jaws", TABLE_MOVIES);
        insertStrings(db, "Rocky", TABLE_MOVIES);
        insertStrings(db, "Die hard", TABLE_MOVIES);
        insertStrings(db, "The terminator", TABLE_MOVIES);

        insertStrings(db, "Full metal jacket", TABLE_MOVIES);
        insertStrings(db, "The big lebowski", TABLE_MOVIES);
        insertStrings(db, "The departed", TABLE_MOVIES);
        insertStrings(db, "Toy story", TABLE_MOVIES);
        insertStrings(db, "The lion king", TABLE_MOVIES);
        insertStrings(db, "Scarface", TABLE_MOVIES);
        insertStrings(db, "Slumdog millionaire", TABLE_MOVIES);
        insertStrings(db, "Monty python and the holy grail", TABLE_MOVIES);
        insertStrings(db, "Aliens", TABLE_MOVIES);
        insertStrings(db, "Avatar", TABLE_MOVIES);

        insertStrings(db, "Titanic", TABLE_MOVIES);
        insertStrings(db, "Jurassic park", TABLE_MOVIES);
        insertStrings(db, "The wizard of oz", TABLE_MOVIES);
        insertStrings(db, "Ghostbusters", TABLE_MOVIES);
        insertStrings(db, "Kill bill", TABLE_MOVIES);
        insertStrings(db, "Finding nemo", TABLE_MOVIES);
        insertStrings(db, "The avengers", TABLE_MOVIES);
        insertStrings(db, "Home alone", TABLE_MOVIES);
        insertStrings(db, "Men in black", TABLE_MOVIES);
        insertStrings(db, "Planet of the apes", TABLE_MOVIES);
    }

    /*
    Inserts data into animals table.
     */
    public void insertAnimals(SQLiteDatabase db) {
        insertStrings(db, "Aardvark", TABLE_ANIMALS);
        insertStrings(db, "Badger", TABLE_ANIMALS);
        insertStrings(db, "Cat", TABLE_ANIMALS);
        insertStrings(db, "Dog", TABLE_ANIMALS);
        insertStrings(db, "Capybara", TABLE_ANIMALS);
        insertStrings(db, "Bat", TABLE_ANIMALS);
        insertStrings(db, "Dingo", TABLE_ANIMALS);
        insertStrings(db, "Cheetah", TABLE_ANIMALS);
        insertStrings(db, "Wolverine", TABLE_ANIMALS);
        insertStrings(db, "Zebra", TABLE_ANIMALS);

        insertStrings(db, "Boar", TABLE_ANIMALS);
        insertStrings(db, "Bear", TABLE_ANIMALS);
        insertStrings(db, "Yak", TABLE_ANIMALS);
        insertStrings(db, "Walrus", TABLE_ANIMALS);
        insertStrings(db, "Vulture", TABLE_ANIMALS);
        insertStrings(db, "Squid", TABLE_ANIMALS);
        insertStrings(db, "Sparrow", TABLE_ANIMALS);
        insertStrings(db, "Tiger", TABLE_ANIMALS);
        insertStrings(db, "Lion", TABLE_ANIMALS);
        insertStrings(db, "Panther", TABLE_ANIMALS);

        insertStrings(db, "Snail", TABLE_ANIMALS);
        insertStrings(db, "Sloth", TABLE_ANIMALS);
        insertStrings(db, "Skunk", TABLE_ANIMALS);
        insertStrings(db, "Tapir", TABLE_ANIMALS);
        insertStrings(db, "Rabbit", TABLE_ANIMALS);
        insertStrings(db, "Snake", TABLE_ANIMALS);
        insertStrings(db, "Possum", TABLE_ANIMALS);
        insertStrings(db, "Otter", TABLE_ANIMALS);
        insertStrings(db, "Iguana", TABLE_ANIMALS);
        insertStrings(db, "Kangaroo", TABLE_ANIMALS);

        insertStrings(db, "Elephant", TABLE_ANIMALS);
        insertStrings(db, "Mouse", TABLE_ANIMALS);
        insertStrings(db, "Deer", TABLE_ANIMALS);
        insertStrings(db, "Dodo", TABLE_ANIMALS);
        insertStrings(db, "Duck", TABLE_ANIMALS);
        insertStrings(db, "Ant", TABLE_ANIMALS);
        insertStrings(db, "Goat", TABLE_ANIMALS);
        insertStrings(db, "Horse", TABLE_ANIMALS);
        insertStrings(db, "Hamster", TABLE_ANIMALS);
        insertStrings(db, "Jaguar", TABLE_ANIMALS);
    }

    /*
    Inserts data into professions table.
     */
    public void insertProfessions(SQLiteDatabase db) {
        insertStrings(db, "Accountant", TABLE_PROFESSIONS);
        insertStrings(db, "Animal carer", TABLE_PROFESSIONS);
        insertStrings(db, "Architect", TABLE_PROFESSIONS);
        insertStrings(db, "Bank clerk", TABLE_PROFESSIONS);
        insertStrings(db, "Plumber", TABLE_PROFESSIONS);
        insertStrings(db, "Taxi driver", TABLE_PROFESSIONS);
        insertStrings(db, "Surgeon", TABLE_PROFESSIONS);
        insertStrings(db, "Secretary", TABLE_PROFESSIONS);
        insertStrings(db, "Restaurant cook", TABLE_PROFESSIONS);
        insertStrings(db, "Police inspector", TABLE_PROFESSIONS);

        insertStrings(db, "Lawyer", TABLE_PROFESSIONS);
        insertStrings(db, "Judge", TABLE_PROFESSIONS);
        insertStrings(db, "Meteorologist", TABLE_PROFESSIONS);
        insertStrings(db, "Teacher", TABLE_PROFESSIONS);
        insertStrings(db, "Pest controller", TABLE_PROFESSIONS);
        insertStrings(db, "Nurse", TABLE_PROFESSIONS);
        insertStrings(db, "Hairdresser", TABLE_PROFESSIONS);
        insertStrings(db, "Firefighter", TABLE_PROFESSIONS);
        insertStrings(db, "Carpenter", TABLE_PROFESSIONS);
        insertStrings(db, "Pilot", TABLE_PROFESSIONS);

        insertStrings(db, "Flight attendant", TABLE_PROFESSIONS);
        insertStrings(db, "Welder", TABLE_PROFESSIONS);
        insertStrings(db, "Researcher", TABLE_PROFESSIONS);
        insertStrings(db, "Sales assistant", TABLE_PROFESSIONS);
        insertStrings(db, "Receptionist", TABLE_PROFESSIONS);
        insertStrings(db, "Psychologist", TABLE_PROFESSIONS);
        insertStrings(db, "Musician", TABLE_PROFESSIONS);
        insertStrings(db, "Painter", TABLE_PROFESSIONS);
        insertStrings(db, "Programmer", TABLE_PROFESSIONS);
        insertStrings(db, "Designer", TABLE_PROFESSIONS);

        insertStrings(db, "Animator", TABLE_PROFESSIONS);
        insertStrings(db, "Astronomer", TABLE_PROFESSIONS);
        insertStrings(db, "Baker", TABLE_PROFESSIONS);
        insertStrings(db, "Electrician", TABLE_PROFESSIONS);
        insertStrings(db, "Veterinarian", TABLE_PROFESSIONS);
        insertStrings(db, "Tanner", TABLE_PROFESSIONS);
        insertStrings(db, "Social worker", TABLE_PROFESSIONS);
        insertStrings(db, "Security guard", TABLE_PROFESSIONS);
        insertStrings(db, "Photographer", TABLE_PROFESSIONS);
        insertStrings(db, "Modeller", TABLE_PROFESSIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*
    Inserts given string content into designated table (name given by category picker).
     */
    private void insertStrings(SQLiteDatabase db, String content, String TABLE_NAME) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CONTENT, content);
        db.insert(TABLE_NAME, null, contentValues);
    }

    /*
    Gets a readable database and executes raw query fetching a string content from a wanted table.
     */
    public Cursor getOneString(int id, String TABLE_NAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }
}
