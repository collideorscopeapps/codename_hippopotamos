package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import it.collideorscopeapps.codename_hippopotamos.model.Playlist;
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

    // TODO check how to handle/synch/check db version here and in the db created from sql file
    // TODO
    public static final int DATABASE_VERSION = 3;
    private final Context myContext; //TODO check if final is necessary

    private TreeMap<Integer, Schermata> schermate;

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    private ArrayList<Playlist> playlists;

    public DBManager(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);

        this.myContext = context;

        createDBFromSqlFile(myContext, null);
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        this.myContext = context;
    }



    private static SQLiteDatabase openDBWithFKConstraints(String path,
                                                          SQLiteDatabase.CursorFactory cf,
                                                          int flags) {

        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, cf, flags);

        db.setForeignKeyConstraintsEnabled(true);

        return db;

    }

    public static Boolean dropTables(Context myContext) {
        String dbPath = myContext.getDatabasePath(DB_NAME).getPath();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        db.setForeignKeyConstraintsEnabled(true);

        return DBManager.dropTablesHelper(myContext, db);
    }

    public static Boolean dropTablesHelper(Context myContext, SQLiteDatabase myDatabase) {

        Boolean dropPerformed = false;

        AssetManager assetManager = myContext.getAssets();
        TreeMap<Integer,String> dropSchemaStatements = Utils.getSingleLineSqlStatementsFromInputStream(
                assetManager,
                Utils.DROP_SCHEMA_SQL_FILE);

        Log.w("DBManager","Dropping DB tables..");
        myDatabase.beginTransaction();
        try {
            for(int i=0;i<dropSchemaStatements.size();i++) {
                String statement = dropSchemaStatements.get(i);
                try {
                    myDatabase.execSQL(statement);
                } catch (Exception e) {
                    Log.e("DB Manager", statement);
                    Log.e("DBManager", e.toString());
                    throw e;
                }
            }

            myDatabase.setTransactionSuccessful();
            dropPerformed = true;
            Log.d("DBManager", "Dropped DB schema and data.");

        } finally {
            Log.v("DBManager", "Ending DB transaction..");
            myDatabase.endTransaction();
            Log.v("DBManager", "DB transaction ended. Closing db..");
            // TODO check: removed all close statements,
            //  except the one after getting the data from the db
            //myDatabase.close();
            //Log.v("DBManager", "DB transaction ended. DB closed.");
            //TODO check if close causes errors if reopening
        }

        return dropPerformed;
    }

    private static SQLiteDatabase ensureDBOpen(Context myContext, SQLiteDatabase db) {

        //TODO check the need for this, add tests. was giving error when db closed
        // when db file manually deleted before runnning tests
        return ensureDBOpen(myContext,db,SQLiteDatabase.OPEN_READONLY);

    }

    private static SQLiteDatabase ensureDBOpen(Context myContext,
                                               SQLiteDatabase db,
                                               int openMode) {

        //TODO check the need for this, add tests. was giving error when db closed
        // when db file manually deleted before runnning tests

        if(db == null || !db.isOpen()) {
            String dbPath = myContext.getDatabasePath(DB_NAME).getPath();
            db = openDBWithFKConstraints(dbPath, null, openMode);
        }

        return db;
    }

    public static boolean createDBFromSqlFile(Context myContext,
                                              SQLiteDatabase myDatabase) {
        //TODO use DB version, check it to avoid creating db every time app starts

        Boolean creationPerformed = false;

        String dbPath = myContext.getDatabasePath(DB_NAME).getPath();

        Log.v("DBManager", "Opening DB..");
        if(myDatabase == null || !myDatabase.isOpen()) {
            myDatabase = openDBWithFKConstraints(
                    dbPath,
                    null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);
        }

        int myDBVersion = myDatabase.getVersion();
        if(myDBVersion== DATABASE_VERSION) {
            Log.v("DBManager", "DB version up to date: " + myDBVersion);
        }
        else {
            Log.v("DBManager", "Should drop older DB, myDBVersion: " + myDBVersion
                    + ", new version: " + DATABASE_VERSION);
            dropTablesHelper(myContext, myDatabase);
            //deleteExistingDatabase(myContext);
        }

        // check if tables are there
        Boolean dbIsEmpty = isDBEmpty(myContext, myDatabase);
        if(!dbIsEmpty) {
            Log.v("DBManager", "DB is already populated");
            creationPerformed = true;
            return creationPerformed;
        }

        Log.v("DBManager", "DB is empty, remaking schema and data from Sql file..");

        return runSchemaCreationQueriesHelper(myContext, myDatabase);
    }

    private static boolean runSchemaCreationQueriesHelper(
            Context myContext,
            SQLiteDatabase myDatabase) {

        boolean creationPerformed = false;

        AssetManager assetManager = myContext.getAssets();
        ArrayList<String> schemaStatements = Utils.getSchemaCreationStatementsFromSqlFile(assetManager);
        TreeMap<Integer,String> dataInsertStatements = Utils.getSingleLineSqlStatementsFromInputStream(
                assetManager, Utils.DATA_INSERT_SQL_FILE);

        myDatabase = ensureDBOpen(myContext, myDatabase, SQLiteDatabase.OPEN_READWRITE);
        myDatabase.beginTransaction();
        try {
            execSchemaCreationQueries(myDatabase, schemaStatements);

            // here we can execute one line at a time (statements are single-line
            for(int i=0;i<dataInsertStatements.size();i++) {
                String statement = dataInsertStatements.get(i);
                try {
                    myDatabase.execSQL(statement);
                } catch (Exception e) {
                    Log.e("DB Manager", statement);
                    Log.e("DBManager", e.toString());
                    throw e;
                }
            }

            myDatabase.setTransactionSuccessful();
            creationPerformed = true;
            Log.v("DBManager", "Successfully created DB schema and inserted data.");

        } finally {
            Log.v("DBManager", "Ending DB transaction..");
            myDatabase.endTransaction();
            Log.v("DBManager", "DB transaction ended. Closing db..");
            // TODO we should not close it after tables creation, only
            // after getting the data
            // TODO check: removed all close statements,
            //  except the one after getting the data from the db
            // myDatabase.close();
            // Log.v("DBManager", "DB transaction ended. DB closed.");

            //Boolean isDbLockedByCurrentThread = myDatabase.isDbLockedByCurrentThread();
            //Boolean isStillOpen = myDatabase.isOpen();
            //List<Pair<String, String>> attachedDbs = myDatabase.getAttachedDbs();
        }

        return creationPerformed;
    }

    public static void execSchemaCreationQueries(SQLiteDatabase myDatabase,
                                                 ArrayList<String> schemaStatements) {
        for (String statement : schemaStatements) {
            //Log.v("DBManager",statement);
            myDatabase.execSQL(statement);
            //Log.v("DBManager",getConcatTableNames(myDatabase));
        }
    }

    public static boolean isDBEmpty(Context myContext, SQLiteDatabase db) {

        Boolean isDBEmpty = true;

        // TODO add test for this case, was giving error when db closed
        // gives error when db file manually deleted before runnning tests
        db = ensureDBOpen(myContext, db);

        String tableNamesConcat = getConcatTableNames(db);
        isDBEmpty = !tableNamesConcat.contains("v_schermate_and_quotes,");
        return isDBEmpty;
    }

    public TreeMap<Integer, Schermata> getSchermate(DBManager.Languages language) {

        // todo, translations languages
        // TODO some fields use a default language if the preferred one is absent

        if(this.schermate != null) {
            return this.schermate;
        }

        //myCreateDBFromSqlFile();
        //openDatabaseReadonly();

        TreeMap<Integer, Schermata> newSchermate = new TreeMap<Integer, Schermata>();

        TreeMap<Integer, String> linguisticNotes = getLinguisticNotes(language);
        TreeMap<Integer, String> easterEggComments = getEasterEggComments(language);
        this.playlists = getPlaylistsFromDB();

        // TODO rededish, review: problems with db creation,
        //  opening, state, and closing cycles
        String quotesAndSchermateQuery = "SELECT * FROM v_schermate_and_quotes";
        SQLiteDatabase db = ensureDBOpen(myContext,null);
        if(isDBEmpty(myContext,db)) {
            runSchemaCreationQueriesHelper(myContext, db);
        }
        try(Cursor cursor = db.rawQuery(quotesAndSchermateQuery, null)) { // todo merge try

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                addQuoteAndSchermata(cursor, newSchermate, linguisticNotes, easterEggComments);
                cursor.moveToNext();
            }
        }
        catch (SQLiteException sqle) {
            Log.e("DBManager", sqle.toString());
            Log.e("DBManager", "Tables: " + getConcatTableNames(db));
        }
        catch (Exception e) {
            Log.e("DBManager", e.toString());
        }

        this.schermate = newSchermate;
        for(Playlist pl :this.playlists) {
            pl.setSchermate(this.schermate);
        }

        db.close();//not needed anymore once data is loaded
        return this.schermate;
    }

    private static String getConcatTableNames(SQLiteDatabase db) {

        String allTableNamesQuery = "SELECT name " +
                "FROM sqlite_master " +
                "WHERE type='table' OR type = 'view' ";
        //ArrayList<String> tableNames = new ArrayList<>();
        String tableNamesConcat = "";
        int tablesCount = 0;
        Cursor c = db.rawQuery(allTableNamesQuery, null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                int firstColumnIdx = 0;
                String tableName = c.getString(firstColumnIdx);
                //tableNames.add(tableName);
                tableNamesConcat += tableName + ",";
                tablesCount++;
                c.moveToNext();
            }
        }
        Log.v("DBManager","Tables in DB ("
                + tablesCount + ") " + tableNamesConcat);

        //if(tablesCount < 3) {isDBEmpty = true;}

        return tableNamesConcat;
    }

    private static void addQuoteAndSchermata(Cursor cursor,
                                             TreeMap<Integer,Schermata> schermate,
                                             TreeMap<Integer,String> linguisticNotes,
                                             TreeMap<Integer,String> easterEggComments) {

        final int schermataIdColIdx = cursor.getColumnIndex("s_id");
        final int greekQuoteIddColIdx = cursor.getColumnIndex("gq_id");
        final int quoteColIdx = cursor.getColumnIndex("quote");
        final int phoneticColIdx = cursor.getColumnIndex("phoneticTranscription");
        final int positionColIdx = cursor.getColumnIndex("position");
        final int titleColIdx = cursor.getColumnIndex("title");
        final int descriptionColIdx = cursor.getColumnIndex("description");
        final int defaultTranslationColIdx = cursor.getColumnIndex("default_translation");
        final int citColIdx = cursor.getColumnIndex("cit");
        final int audioFileNameColIdx = cursor.getColumnIndex("audioFileName");

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
        String eeComment = easterEggComments.get(idSchermata);

        // NB schermata translation is used in place of those of each quote
        //TODO change this on the basis of user language preferences
        String defaultTranslation = cursor.getString(defaultTranslationColIdx);
        Log.v("DBManager","Translation: " + defaultTranslation);

        Quote currentQuote = new Quote(idQuote, quotePosition, greekQuote,
                phoneticTranscription, audioFileName);

        Schermata currentSchermata = schermate.get(idSchermata);
        if(null == currentSchermata) {
            currentSchermata= new Schermata(
                    idSchermata,
                    title,
                    description,
                    defaultTranslation,
                    linguisticNote,
                    cit,
                    eeComment);
            schermate.put(idSchermata, currentSchermata);
        }
        currentSchermata.addQuote(currentQuote);
    }

    private ArrayList<Playlist> getPlaylistsFromDB() {

        ArrayList<Playlist> playlists = new ArrayList<>();

        try(SQLiteDatabase db = getReadableDatabase()) {

            String playlistsQuery = "SELECT * FROM v_playlists";
            Cursor cursor = db.rawQuery(playlistsQuery, null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {

                Integer playlistId = cursor.getInt(cursor.getColumnIndex("p_id"));
                String description = cursor.getString(cursor.getColumnIndex("description"));

                String schermateConcat = cursor.getString(cursor.getColumnIndex("schermate"));
                String playOrderConcat = cursor.getString(cursor.getColumnIndex("sorting"));

                TreeSet<Integer> schermateIds = Utils.getIntsFromConcatString(schermateConcat);
                TreeSet<Integer> playOrderRanks = Utils.getIntsFromConcatString(playOrderConcat);

                TreeMap<Integer, Integer> playListAsRankedSchermate = new TreeMap<>();
                Iterator<Integer> schermateIdsItr = schermateIds.iterator();
                Iterator<Integer> playOrderRanksItr = playOrderRanks.iterator();
                while(schermateIdsItr.hasNext()) {
                    int key = schermateIdsItr.next();
                    int value = playOrderRanksItr.next();

                    playListAsRankedSchermate.put(key, value);
                }

                Playlist currentPlaylist = new Playlist(description, playListAsRankedSchermate);

                playlists.add(currentPlaylist);
                cursor.moveToNext();
            }
        }

        return playlists;
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

    public TreeMap<Integer, String> getLinguisticNotes(Languages language) {

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

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        createDBFromSqlFile(myContext, sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // TODO
    }


}
