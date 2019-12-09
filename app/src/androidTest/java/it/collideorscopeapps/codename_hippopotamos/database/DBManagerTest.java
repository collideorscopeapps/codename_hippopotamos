package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

import static org.junit.Assert.*;

public class DBManagerTest {

    @Before
    public void setUp() throws Exception {

        deleteExistingDatabase();

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBManager.createDBFromSqlFile(appContext,null);
    }

    @After
    public void tearDown() throws Exception {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBManager dbManager;
        dbManager = new DBManager(appContext);
        dbManager.deleteExistingDatabase(appContext);
    }

    @Test
    public void deleteExistingDatabase() {

        //TODO add test in case db is alredy open but old version
        // was giving error when try to open in read mode, was locked

    }

    @Test
    public void openDatabaseReadonly() {
    }

    @Test
    public void closeDatabase() {
    }

    @Test
    public void createDBFromSqlFile() {
    }

    @Test
    public void getSchermate() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBManager dbManager;
        dbManager = new DBManager(appContext);
        TreeMap<Integer, Schermata> schermate = dbManager.getSchermate(DBManager.Languages.EN);

        int min_num_schermate = 14;
        boolean min_schermate = schermate.size() >= min_num_schermate;

        assertTrue(min_schermate);
    }

    @Test
    public void getEasterEggComments() {
    }

    @Test
    public void getLinguisticNotes() {
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onUpgrade() {
    }
}