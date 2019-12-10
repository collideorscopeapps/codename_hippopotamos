package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;

import androidx.test.filters.Suppress;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

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

        int min_num_schermate = 27;
        boolean min_schermate = schermate.size() >= min_num_schermate;

        String failMessage = "Expected " + min_num_schermate + " schermate, found " + schermate.size();
        assertTrue(failMessage,min_schermate);
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