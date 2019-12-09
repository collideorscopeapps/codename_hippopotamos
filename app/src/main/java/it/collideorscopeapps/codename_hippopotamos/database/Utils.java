package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.TreeSet;


public class Utils {

    final static String DATA_INSERT_SQL_FILE = "greekquotes.dbdata.sql";
    final static String SCHEMA_SQL_FILE = "greekquotes.dbschema.sql";

    public static String getShemaCreationQueriesFromSqlFile(AssetManager assetManager) {

        return getQueriesFromSqlFile(assetManager, SCHEMA_SQL_FILE);
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

    public static TreeMap<Integer,String> getSingleLineSqlStatementsFromInputStream(AssetManager assetManager) {
        TreeMap<Integer,String> statements = new TreeMap<>();

        try(InputStream shemaCreationSqlFileInputStream = assetManager.open(DATA_INSERT_SQL_FILE)) {
            //creating an InputStreamReader object
            InputStreamReader isReader = new InputStreamReader(shemaCreationSqlFileInputStream);
            //Creating a BufferedReader object
            BufferedReader reader = new BufferedReader(isReader);

            String statement;
            int statementsCount = 0;
            while((statement = reader.readLine())!= null){

                // check if it's comment line
                boolean isCommentOrEmptyLine = statement.startsWith("--") || statement.isEmpty();
                if(!isCommentOrEmptyLine) {
                    statements.put(statementsCount,statement);
                    statementsCount++;
                }

            }

        } catch (IOException e) {
            Log.e("DB Utils", e.toString());
        }



        return statements;
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
