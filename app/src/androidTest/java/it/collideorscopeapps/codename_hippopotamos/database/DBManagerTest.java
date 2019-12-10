package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.filters.Suppress;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DBManagerTest {

    private Context appContext;
    private DBManager dbManager;

    @Before
    public void setUp() throws Exception {

        this.appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        this.dbManager = new DBManager(appContext);

        DBManager.dropTables(this.appContext);
        DBManager.createDBFromSqlFile(appContext,null);
    }


    @After
    public void tearDown() throws Exception {

        DBManager.dropTables(this.appContext);
    }

    @Ignore("Not implemented")
    @Suppress
    @Test
    public void deleteExistingDatabase() {

        //TODO add test in case db is alredy open but old version
        // was giving error when try to open in read mode, was locked

    }

    @Test@Suppress
    public void openDatabaseReadonly() {
    }

    @Test@Suppress
    public void createDBFromSqlFile() {
    }

    @Test
    public void getSchermate() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBManager dbManager;
        dbManager = new DBManager(appContext);
        TreeMap<Integer, Schermata> schermate = dbManager.getSchermate(DBManager.Languages.EN);

        int extectedMinNumSchermate = 27;
        int extectedMinNumQuotes = 32;
        int maxSchermate = 100;
        int maxQuotes = 100;
        checkSchermate(schermate,extectedMinNumSchermate, maxSchermate);
        checkQuotes(schermate,extectedMinNumQuotes,maxQuotes);
    }

    @Test
    public void checkFreshResetDBHasNoRows() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String schemaQueries = Utils.getShemaCreationQueriesFromSqlFile(appContext.getAssets());

        String dbPath = appContext.getDatabasePath(DBManager.DB_NAME).getPath();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);

        DBManager.dropTables(this.appContext);//DBManager.dropTablesHelper(appContext, db);
        DBManager.execSchemaCreationQueries(db,schemaQueries);

        // check schermate, quotes are empty
        TreeMap<Integer, Schermata> schermate = dbManager.getSchermate(DBManager.Languages.EN);
        checkSchermate(schermate,0, 0);
        checkQuotes(schermate,0,0);
    }

    private void checkSchermate(TreeMap<Integer, Schermata> schermate,
                                int extectedMinNumSchermate, int expectedMax) {

        boolean enoughSchermate = schermate.size() >= extectedMinNumSchermate;

        String failMessageSchermate = "Expected " + extectedMinNumSchermate
                + " schermate, found " + schermate.size();
        assertTrue(failMessageSchermate,enoughSchermate);

        boolean tooManySchermate = schermate.size() > expectedMax;

        String failMsgTooManySchermate = "Expected max " + expectedMax
                + " schermate, found " + schermate.size();
        assertFalse(failMsgTooManySchermate,tooManySchermate);
    }

    private void checkQuotes(TreeMap<Integer, Schermata> schermate,
                             int extectedMinNumQuotes, int expectedMax) {
        int quotesCount = 0;
        for(Schermata schermata:schermate.values()) {
            quotesCount += schermata.getQuotes().size();
        }

        // a quote can be reused in more screens
        // so the quote count can be higher here
        boolean enoughQuotes = quotesCount >= extectedMinNumQuotes;

        String failMessageQuotes = "Expected " + extectedMinNumQuotes
                + " quotes, found " + quotesCount;
        assertTrue(failMessageQuotes,enoughQuotes);

        boolean tooManyQuotes = quotesCount > expectedMax;

        String failMsgTooManyQuotes = "Expected max " + expectedMax
                + " quotes, found " + quotesCount;
        assertFalse(failMsgTooManyQuotes,tooManyQuotes);
    }

    @Test
    public void getEasterEggComments() {

        DBManager.Languages ENG = DBManager.Languages.EN;

        TreeMap<Integer, String> easterEggComments = dbManager.getEasterEggComments(ENG);

        int min_num_eec = 1;
        boolean min_eec = easterEggComments.size() >= min_num_eec;

        assertTrue(min_eec);
    }

    @Test
    public void getLinguisticNotes() {

        DBManager.Languages ENG = DBManager.Languages.EN;
        TreeMap<Integer, String> linguisticNotes = dbManager.getLinguisticNotes(ENG);

        int min_num_ln = 5;
        boolean min_ln = linguisticNotes.size() >= min_num_ln;

        assertTrue(min_ln);
    }

    @Test@Suppress
    public void onCreate() {

        // TODO test, this was not being called after DBManager instance creation
        // so in the DBManager construction had to call db creation from sql file
    }

    @Test@Suppress
    public void onUpgrade() {
    }
}