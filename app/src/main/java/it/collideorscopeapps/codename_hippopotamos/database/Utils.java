package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;


public class Utils {

    final static String DATA_INSERT_SQL_FILE = "greekquotes.dbdata.sql";
    final static String DROP_SCHEMA_SQL_FILE = "greekquotes.dropschema.sql";
    public final static String SCHEMA_SQL_FILE = "greekquotes.dbschema.sql";

    public static String getPrettifiedReadingList() {

    /*
    *  --TODO get a playlist, write java utility to print (to txt) reading list from playlist
    --(CASE (SELECT count(*) FROM playlists_schermate )
    --     WHEN 0 THEN 0
    --     WHEN NULL THEN 0
    --     ELSE (SELECT max(sorting)+1 FROM playlists_schermate )
    --    END)
    * */

        // get data (schermate in a play list)
        // for each schermata print:
        // description/title
        // quote series in the schermata
        // cit
        // some note apt for the reader

        return null;
    }

    public static ArrayList<String> getSchemaCreationStatementsFromSqlFile(
            AssetManager assetManager) {

        return getStatementsFromSqlFile(assetManager, SCHEMA_SQL_FILE);
    }

    private static ArrayList<String> getStatementsFromSqlFile(
            AssetManager assetManager,
            String assetFileName) {

        try(InputStream shemaCreationSqlFileInputStream = assetManager.open(assetFileName)) {

            return getStatementsFromInputStream(shemaCreationSqlFileInputStream);
        }
        catch (IOException e) {
            Log.e("Utils", e.toString());
        }

        return null;
    }

    public static TreeMap<Integer,String> getSingleLineSqlStatementsFromInputStream(
            AssetManager assetManager,
            String fileName) {
        TreeMap<Integer,String> statements = new TreeMap<>();

        try(InputStream shemaCreationSqlFileInputStream = assetManager.open(fileName)) {
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

    private static ArrayList<String> getStatementsFromInputStream(InputStream is) {

        // we need to split by semicolons only in schema creation statements, which can be multiline
        final String STATEMENTS_SEPARATOR = "--/";

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

        String concatQueries = sb.toString();
        ArrayList<String> sqlStatements = new ArrayList<>();

        for (String statement : concatQueries.split(STATEMENTS_SEPARATOR)) {

            sqlStatements.add(statement);
        }

        return sqlStatements;
    }

}
