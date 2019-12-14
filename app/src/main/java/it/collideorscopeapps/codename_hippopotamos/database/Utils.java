package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.content.res.AssetManager;
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


public class Utils {

    final static String DATA_INSERT_SQL_FILE = "greekquotes.dbdata.sql";
    final static String DROP_SCHEMA_SQL_FILE = "greekquotes.dropschema.sql";
    public final static String SCHEMA_SQL_FILE = "greekquotes.dbschema.sql";

    public static void appendLineToStringBuilder(StringBuilder sb, String str) {

        if(str != null) {
            sb.append(str);
            sb.append("\n");
        }
    }

    public static boolean castSqliteBoolean(int value) {

        boolean isTrue = value != 0;

        return isTrue;
    }

    public static String getPrettifiedReadingList(Context appContext) {

        DBManager db = new DBManager(appContext);

        TreeMap<Integer, Schermata> schermate = db.getSchermate(DBManager.Languages.EN);
        ArrayList<Playlist> playlists = db.getPlaylists();

        StringBuilder sb = new StringBuilder();
        for(Playlist pl : playlists) {

            if(!pl.isDisabled()) {
                appendPlaylistToStringBuilder(sb, pl);
            }
        }

        return sb.toString();
    }

    private static void appendPlaylistToStringBuilder(StringBuilder sb,
                                                      Playlist pl) {
        final String emptyLine = "";
        final String gitHubHeadings = "### ";
        final String quoteStart = "> **";
        final String boldEnd = "**";
        appendLineToStringBuilder(sb, gitHubHeadings + pl.getDescription());
        appendLineToStringBuilder(sb, emptyLine);

        TreeMap<Integer, Schermata> rankedSchermate = pl.getRankedSchermate();

        for(Integer schermataRank: rankedSchermate.keySet()) {
            Schermata currentSchermata = rankedSchermate.get(schermataRank);

            appendLineToStringBuilder(sb, currentSchermata.getTitle());
            //appendLineToStringBuilder(sb, currentSchermata.getDescription());

            appendLineToStringBuilder(sb, emptyLine);
            appendLineToStringBuilder(sb, quoteStart
                    + currentSchermata.getQuotesAsString()
                    + boldEnd);
            appendLineToStringBuilder(sb, emptyLine);

            // TODO (in DB manager and queries) translation by selected user language
            appendLineToStringBuilder(sb, currentSchermata.getTranslation());
            appendLineToStringBuilder(sb, currentSchermata.getCitation());
            appendLineToStringBuilder(sb, currentSchermata.getLinguisticNotes());
            appendLineToStringBuilder(sb, currentSchermata.getEasterEggComment());
            //TODO? add some other note apt for the reading list reader

            appendLineToStringBuilder(sb, emptyLine);
        }

        appendLineToStringBuilder(sb, emptyLine);
        appendLineToStringBuilder(sb, emptyLine);
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
