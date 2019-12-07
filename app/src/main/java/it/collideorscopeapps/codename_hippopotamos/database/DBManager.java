package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class DBManager extends SQLiteOpenHelper {

    public final Languages DEFAULT_LANGUAGE = Languages.EN;
    public enum Languages {
        //TODO check if numeric values cna be explicity assigned
        //TODO to ensure they matche the db
        NO_LANGUAGE, // = 0
        EN, // = 1
        IT // = 2
    }

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


    public TreeMap<Integer, Schermata> getSchermate(DBManager.Languages language) {

        // todo, translations languages
        // TODO some fields use a default language if the preferred one is absent

        myCreateDBFromSqlFile();
        openDatabaseReadonly();

        int schermateCount = (int)DatabaseUtils.queryNumEntries(myDatabase,
                "schermate");
        TreeMap<Integer, Schermata> schermate = new TreeMap<Integer, Schermata>();

        TreeMap<Integer, String> linguisticNotes = getLinguisticNotes(language);
        TreeMap<Integer, String> getEasterEggComments = getEasterEggComments(language);

        String quotesAndSchermateQuery = "SELECT * FROM v_schermate";
        try(Cursor cursor = myDatabase.rawQuery(quotesAndSchermateQuery, null)) {

            final int schermataIdColIdx = cursor.getColumnIndex("s_id");
            final int greekQuoteIddColIdx = cursor.getColumnIndex("gq_id");
            final int quoteColIdx = cursor.getColumnIndex("quote");
            final int phoneticColIdx = cursor.getColumnIndex("phoneticTranscription");
            final int positionColIdx = cursor.getColumnIndex("position");
            final int titleColIdx = cursor.getColumnIndex("title");
            final int descriptionColIdx = cursor.getColumnIndex("description");
            final int citColIdx = cursor.getColumnIndex("cit");
            final int audioFileNameColIdx = cursor.getColumnIndex("audioFileName");

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                int idSchermata = cursor.getInt(schermataIdColIdx);
                int idQuote = cursor.getInt(greekQuoteIddColIdx);
                String greekQuote = cursor.getString(quoteColIdx);
                String phoneticTranscription = cursor.getString(phoneticColIdx);
                int quotePosition = cursor.getInt(positionColIdx);
                String title = cursor.getString(titleColIdx);
                String description = cursor.getString(descriptionColIdx);
                String cit = cursor.getString(citColIdx);
                String audioFileName = cursor.getString(audioFileNameColIdx);

                String linguisticNote = linguisticNotes.get(idSchermata);
                String eeComment = getEasterEggComments.get(idSchermata);

                Quote currentQuote = new Quote(idQuote, quotePosition, greekQuote,
                        phoneticTranscription, audioFileName);

                Schermata currentSchermata = schermate.get(idSchermata);
                if(null == currentSchermata) {
                    currentSchermata= new Schermata(
                            idSchermata,
                            title,
                            description,
                            linguisticNote,
                            cit,
                            eeComment);
                    schermate.put(idSchermata, currentSchermata);
                }
                currentSchermata.addQuote(currentQuote);

                cursor.moveToNext();
            }
        }
        catch (Exception e) {
            Log.e("DBManager", e.toString());
        }

        return schermate;
    }

    public  TreeMap<Integer, String> getEasterEggComments(Languages language) {

        // TODO use eeComment in the default language when it's the only one

        TreeMap<Integer, String> eeComments = new TreeMap<Integer, String>();

        try(SQLiteDatabase db = getReadableDatabase()) {

            String eeCommentsQuery = "SELECT * FROM easter_egg_comments WHERE language_id = "
                    + language.ordinal();
            Cursor cursor = db.rawQuery(eeCommentsQuery, null);

            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {

                Integer schermataId = cursor.getInt(cursor.getColumnIndex("schermata_id"));
                String eeComment = cursor.getString(cursor.getColumnIndex("eeComment"));
                eeComments.put(schermataId, eeComment);

                cursor.moveToNext();
            }
        }

        return eeComments;
    }

    public  TreeMap<Integer, String> getLinguisticNotes(Languages language) {

        TreeMap<Integer, String> linguisticNotes = new TreeMap<Integer, String>();

        try(SQLiteDatabase db = getReadableDatabase()) {

            String linguisticNotesQuery = "SELECT * FROM linguistic_notes WHERE language_id = "
                    + language.ordinal();
            Cursor cursor = db.rawQuery(linguisticNotesQuery, null);

            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {

                Integer schermataId = cursor.getInt(cursor.getColumnIndex("schermata_id"));
                String linguisticNote = cursor.getString(cursor.getColumnIndex("linguisticNote"));

                linguisticNotes.put(schermataId, linguisticNote);

                cursor.moveToNext();
            }
        }
        return linguisticNotes;
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
        myCreateDBFromSqlFile();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // TODO
    }


}
