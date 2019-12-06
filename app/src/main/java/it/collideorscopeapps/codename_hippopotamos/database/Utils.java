package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Utils {

    public static String getShemaCreationQueriesFromSqlFile(AssetManager assetManager) {

        final String SCHEMA_SQL_FILE = "greekquotes.dbschema.sql";
        return getQueriesFromSqlFile(assetManager, SCHEMA_SQL_FILE);
    }

    public static String getDataInsertionQueriesFromSqlFile(AssetManager assetManager) {

        final String DATA_INSERT_SQL_FILE = "greekquotes.dbdata.sql";
        return getQueriesFromSqlFile(assetManager, DATA_INSERT_SQL_FILE);
    }

    private static String getQueriesFromSqlFile(AssetManager assetManager, String assetFileName) {

        try(InputStream shemaCreationSqlFileInputStream = assetManager.open(assetFileName)) {

            return getQueriesFromInputStream(shemaCreationSqlFileInputStream);
        }
        catch (IOException e) {
            Log.e("Utils", e.toString());
        }

        return null;
    }

    private static String getQueriesFromInputStream(InputStream is) {
        String queries = "";

        //creating an InputStreamReader object
        InputStreamReader isReader = new InputStreamReader(is);
        //Creating a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();

        try {
            String str;
            while((str = reader.readLine())!= null){
                sb.append(str);
            }

        } catch (IOException e) {
            Log.e("DB Utils", e.toString());
        }

        queries = sb.toString();

        return queries;
    }

}
