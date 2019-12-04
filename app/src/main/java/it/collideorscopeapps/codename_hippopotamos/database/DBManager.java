package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import it.collideorscopeapps.codename_hippopotamos.model.Quote;

public class DBManager extends SQLiteOpenHelper {


    // TODO copy db from assets folder to data/data

    public static final String DB_NAME = "greekquotes.sqlite";
    public static final String DB_LOCATION = "/data/data" +
            "/it.collideorscopeapps.codename_hippopotamos" +
            "/databases/";

    public static final int DATABASE_VERSION = 1;
    private final Context myContext; //TODO check if final is necessary
    private SQLiteDatabase myDatabase;


    public DBManager(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);

        this.myContext = context;
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        this.myContext = context;
    }

    public void openDatabase() {

        String dbPath = myContext.getDatabasePath(DB_NAME).getPath();

        if(myDatabase != null && myDatabase.isOpen()) {
            return;
        }

        myDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);


    }

    public void closeDatabase () {

        if(myDatabase != null) {
            myDatabase.close();
        }
    }

    public List<Quote> getQuotes() {

        return null;
    }

    @Deprecated
     private void tryReadDB() throws IOException {


        File dbFile = myContext.getDatabasePath(DB_NAME);

        if (!dbFile.exists()) {

            InputStream myInput = this.myContext.getAssets().open(DB_NAME);
            myInput.close();
        }

        String appDataPath = myContext.getApplicationInfo().dataDir;


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableGreekQuores = "CREATE TABLE greek_quotes (id PRIMARY KEY AUTOINCREMENT, " +
                "quoteText TEXT, " +
                "EEcomment TEXT, " + //Easter Egg comment
                "grAudioResFileName TEXT)";

        String createTableTranslationLanguages = "CREATE TABLE translation_languages (id PRIMARY KEY AUTOINCREMENT, " +
                "quoteText TEXT, " +
                "EEcomment TEXT, " + //Easter Egg comment
                "grAudioResFileName TEXT)";

        sqLiteDatabase.execSQL(createTableGreekQuores);
        sqLiteDatabase.execSQL(createTableTranslationLanguages);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // TODO
    }


}
