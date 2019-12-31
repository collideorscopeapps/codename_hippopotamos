package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import it.collideorscopeapps.codename_hippopotamos.model.Playlist;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;


public class DBUtils {

    public final static String TAG = "DBUtils";

    final static String DATA_INSERT_SQL_FILE = "greekquotes.dbdata.sql";
    final static String DROP_SCHEMA_SQL_FILE = "greekquotes.dropschema.sql";
    public final static String SCHEMA_SQL_FILE = "greekquotes.dbschema.sql";

    public static void appendLineToStringBuilder(StringBuilder sb,
                                                 String preamble,
                                                 String lineCore) {
        final String doubleNewLine = "\n\n";

        if(lineCore != null) {
            sb.append(preamble + lineCore);
            sb.append(doubleNewLine);
        }
    }

    public static void appendLineToStringBuilder(StringBuilder sb, String str) {

        appendLineToStringBuilder(sb, "", str);
    }

    public static void appendEmptyLineToStringBuilder(StringBuilder sb) {

        appendLineToStringBuilder(sb, "<br />");
    }


    public static boolean castSqliteBoolean(int value) {

        boolean isTrue = value != 0;

        return isTrue;
    }

    public static String getPrettifiedReadingList(Context appContext) {

        QuotesProvider quotesProvider = new QuotesProvider();
        quotesProvider.create(appContext);
        quotesProvider.init();

        TreeMap<Integer, Schermata> schermate
                = quotesProvider.getSchermateById();
        TreeMap<Integer,Playlist> playlists = quotesProvider.getPlaylistsByRank();

        StringBuilder sb = new StringBuilder();
        for(Playlist pl : playlists.values()) {

            if(!pl.isDisabled()) {
                appendPlaylistToStringBuilder(sb, pl);
            }
        }

        return sb.toString();
    }

    private static void appendPlaylistToStringBuilder(StringBuilder sb,
                                                      Playlist pl) {
        final String gitHubHeadings = "### ";
        final String quoteStart = "> **";
        final String boldEnd = "**";
        appendLineToStringBuilder(sb, gitHubHeadings + pl.getDescription());

        TreeMap<Integer, Schermata> rankedSchermate = pl.getRankedSchermate();

        for(Integer schermataRank: rankedSchermate.keySet()) {
            Schermata currentSchermata = rankedSchermate.get(schermataRank);

            appendEmptyLineToStringBuilder(sb);

            appendLineToStringBuilder(sb, currentSchermata.getTitle());
            //appendLineToStringBuilder(sb, currentSchermata.getDescription());

            appendLineToStringBuilder(sb, quoteStart
                    + currentSchermata.getQuotesAsString()
                    + boldEnd);

            // TODO (in DB manager and queries) translation by selected user language
            appendLineToStringBuilder(sb, currentSchermata.getTranslation());
            appendLineToStringBuilder(sb, currentSchermata.getCitation());
            appendLineToStringBuilder(sb,
                    "Linguistic/grammar notes: ",
                    currentSchermata.getLinguisticNotes());
            appendLineToStringBuilder(sb, currentSchermata.getEasterEggComment());
            //TODO? add some other note apt for the reading list reader
        }
    }

    public static TreeSet<Integer> getIntsFromConcatString(String concat) {

        TreeSet<Integer> ints = new TreeSet<>();
        //Todo ordering might not be guaranted in TreeSet, check
        for(String num:concat.split(",")) {

            int numParsed = Integer.parseInt(num);
            ints.add(numParsed);
        }

        return ints;
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
            Log.e("DBUtils", e.toString());
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
            Log.e("DB DBUtils", e.toString());
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
            Log.e("DB DBUtils", e.toString());
        }

        String concatQueries = sb.toString();
        ArrayList<String> sqlStatements = new ArrayList<>();

        for (String statement : concatQueries.split(STATEMENTS_SEPARATOR)) {

            sqlStatements.add(statement);
        }

        return sqlStatements;
    }

    public static String getConcatTableNames(SQLiteDatabase db) {

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
        Log.v(TAG,"Tables in DB ("
                + tablesCount + ") " + tableNamesConcat);

        //if(tablesCount < 3) {isDBEmpty = true;}

        return tableNamesConcat;
    }

    public static boolean isDBEmpty(Context myContext, SQLiteDatabase db) {

        Boolean isDBEmpty = true;
        //..removed stuff ensureDBOpen
        String tableNamesConcat = getConcatTableNames(db);
        isDBEmpty = !tableNamesConcat.contains("v_schermate_and_quotes,");
        return isDBEmpty;
    }
}
