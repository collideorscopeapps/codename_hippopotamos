package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class DBManager extends SQLiteOpenHelper {


    public static final String DB_NAME = "greekquotes";

    // TODO for the audio files, not stored into the db, check how to have them in the sd card only
    // i.e. by downloading them
    // TODO check if there is a default "app data" dir in the sd card, or path conventions
    // TODO make unit/integration tests

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

    public void openDatabaseReadonly() {

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

    public static Boolean createDBFromSqlFile(Context myContext, SQLiteDatabase myDatabase) {
        //TODO use DB version, check it to avoid creating db every time app starts

        Boolean creationPerformed = false;

        String dbPath = myContext.getDatabasePath(DB_NAME).getPath();

        if(myDatabase != null && myDatabase.isOpen()) {
            Log.v("DBManager", "DB aready open, not creating it.");
            return creationPerformed;
        }

        Log.v("DBManager", "Creating DB from Sql file..");

        myDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        AssetManager assetManager = myContext.getAssets();
        String schemaQueries = Utils.getShemaCreationQueriesFromSqlFile(assetManager);
        String dataInsertQueries = Utils.getDataInsertionQueriesFromSqlFile(assetManager);
        myDatabase.beginTransaction();
        try {
            for (String query : schemaQueries.split(";")) {
                myDatabase.execSQL(query);
            }
            for (String query : dataInsertQueries.split(";")) {
                myDatabase.execSQL(query);
            }
            myDatabase.setTransactionSuccessful();
            creationPerformed = true;
            Log.v("DBManager", "Successfully created DB schema and inserted data.");

        } finally {

            myDatabase.endTransaction();
        }

        return creationPerformed;
    }

    private void myCreateDBFromSqlFile() {

        createDBFromSqlFile(myContext, myDatabase);

    }

    public ArrayList<Schermata> getSchermate() {

        myCreateDBFromSqlFile();
        openDatabaseReadonly();

        int schermateCount = (int)DatabaseUtils.queryNumEntries(myDatabase,
                "schermate");
        ArrayList<Schermata> schermate = new ArrayList<Schermata>();

        String quotesAndSchermateQuery = "SELECT * FROM v_schermate";
        Cursor cursor = myDatabase.rawQuery(quotesAndSchermateQuery, null);

        final int idColIdx = cursor.getColumnIndex("s_id");
        final int quoteColIdx = cursor.getColumnIndex("quote");
        final int positionColIdx = cursor.getColumnIndex("position");
        final int descriptionColIdx = cursor.getColumnIndex("description");
        final int citColIdx = cursor.getColumnIndex("cit");
        final int eeCommentColIdx = cursor.getColumnIndex("EEcomment");
        final int audioFileNameColIdx = cursor.getColumnIndex("audioFileName");

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int idSchermata = cursor.getInt(idColIdx);
            String greekQuote = cursor.getString(quoteColIdx);
            int quotePosition = cursor.getInt(positionColIdx);
            String description = cursor.getString(descriptionColIdx);
            String cit = cursor.getString(citColIdx);
            String easterEggComment = cursor.getString(eeCommentColIdx);
            String audioFileName = cursor.getString(audioFileNameColIdx);

            Quote currentQuote = new Quote(quotePosition, greekQuote, audioFileName);
            Schermata currentSchermata = new Schermata(idSchermata,
                        description,
                        cit,
                        easterEggComment);

            int schermataIndexInArray = schermate.indexOf(currentSchermata);
            final int ELEMENT_NOT_FOUND = -1;
            if(schermataIndexInArray == ELEMENT_NOT_FOUND){

                schermate.add(currentSchermata);
                currentSchermata.addQuote(currentQuote);
            }
            else {
                schermate.get(schermataIndexInArray).addQuote(currentQuote);
            }

            cursor.moveToNext();
        }

        return schermate;
    }

 /*   public ArrayList<ContentValues> getMovies() {

        ArrayList<ContentValues> movies = new ArrayList<>();

        try(SQLiteDatabase db = getReadableDatabase()) {

            String query = "SELECT * FROM films";

            Cursor cursor = db.rawQuery(query, null);

            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {

                ContentValues movie = new ContentValues();

                movie.put("id", cursor.getInt(cursor.getColumnIndex("id")));
                movie.put("nome", cursor.getString(cursor.getColumnIndex("nome")));
                movie.put("genere", cursor.getString(cursor.getColumnIndex("genere")));
                movie.put("anno_prod", cursor.getString(cursor.getColumnIndex("anno_prod")));
                movie.put("tipo_supp", cursor.getString(cursor.getColumnIndex("tipo_supp")));
                movie.put("locandina", cursor.getString(cursor.getColumnIndex("locandina")));

                movies.add(movie);

                cursor.moveToNext();
            }
        }

        return movies;

    }*/

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
        myCreateDBFromSqlFile();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // TODO
    }


}
