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

    private void deleteExistingDatabase() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // TODO delete database file
        String dbPath = appContext.getDatabasePath(DBManager.DB_NAME).getPath();
        SQLiteDatabase.deleteDatabase(new File(dbPath));

        String databasesFolder = "/data/data/" + appContext.getPackageName() + "/databases/";
        String dbPath2 = databasesFolder + DBManager.DB_NAME;
        SQLiteDatabase.deleteDatabase(new File(dbPath2));
    }

    @After
    public void tearDown() throws Exception {
        deleteExistingDatabase();
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