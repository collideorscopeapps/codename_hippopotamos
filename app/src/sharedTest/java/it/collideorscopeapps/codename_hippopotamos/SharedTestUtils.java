package it.collideorscopeapps.codename_hippopotamos;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.QuotesProvider;
import it.collideorscopeapps.codename_hippopotamos.model.Playlist;
import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;
import it.collideorscopeapps.codename_hippopotamos.utils.Utils;

//import static com.google.common.truth.Truth.assertThat;
//import com.google.common.truth.Truth.assertThat;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SharedTestUtils {

    public static final String TAG = "SharedTestUtils";

    public static void checkSchermate(TreeMap<Integer, Schermata> schermate,
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


    public static void checkQuotes(TreeMap<Integer, Schermata> schermate,
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


    public static String getPrettifiedReadingList(Context appContext) {

        QuotesProvider quotesProvider = new QuotesProvider();
        quotesProvider.create(appContext);
        quotesProvider.init();

        TreeMap<Integer, Schermata> schermate
                = quotesProvider.getSchermateById();
        TreeMap<Integer, Playlist> playlists = quotesProvider.getPlaylistsByRank();

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

        for (Integer schermataRank : rankedSchermate.keySet()) {
            Schermata currentSchermata = rankedSchermate.get(schermataRank);

            appendEmptyLineToStringBuilder(sb);

            appendLineToStringBuilder(sb, currentSchermata.getTitle());
            //appendLineToStringBuilder(sb, currentSchermata.getDescription());

            String fullQuoteText = getFullQuoteAsString(currentSchermata);
            String shortQuoteText = getShortQuoteAsString(currentSchermata);
            String quoteToUse = fullQuoteText;
            if (Utils.isNullOrEmpty(fullQuoteText)) {
                quoteToUse = shortQuoteText;
            } else if (Utils.isNullOrEmpty(shortQuoteText)) {
                quoteToUse = getWordListAsString(currentSchermata);
            }
            appendLineToStringBuilder(sb, quoteStart
                    + quoteToUse
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

    public static String getShortQuoteAsString(Schermata screen) {
        return getQuoteAsString(screen.getShortQuote());
    }

    public static String getFullQuoteAsString(Schermata screen) {
        return getQuoteAsString(screen.getFullQuote());
    }

    public static String getQuoteAsString(Quote quote) {
        String string = "";
        if(!Utils.isNullOrEmpty(quote)) {
            string = quote.getQuoteText();
        }

        return string;
    }

    public static String getWordListAsString(Schermata screen) {

        String quotesAsString = "";

        int currentQuoteNum = 1;
        for(Quote quote : screen.getQuotes()) {

            String prevSeparator = "";
            String quoteText = quote.getQuoteText();
            String closingComma = "";

            boolean isFirstQuote = currentQuoteNum == 1;
            if(!isFirstQuote) {
                prevSeparator = ", ";
            }

            boolean isLastQuote = currentQuoteNum == screen.getQuotes().size();
            if(isLastQuote) {
                final String comma = ".";
                if(Utils.isNullOrEmpty(quoteText)) {
                    closingComma = comma;
                    quotesAsString += closingComma;
                } else if(!quoteText.endsWith(comma)) {
                    closingComma = comma;
                    quotesAsString += prevSeparator + quoteText + closingComma;
                } else {
                    quotesAsString += prevSeparator + quoteText;
                }
            }
            else {
                quotesAsString += prevSeparator + quoteText;
            }

            currentQuoteNum++;
        }

        return quotesAsString;
    }

    public static int getTableRowsCount(Context context, String tableName) {
        int rowsCount = -1;
        SQLiteOpenHelper mOpenHelper = QuotesProvider.createDBOpenHelper(context);
        try(SQLiteDatabase db = mOpenHelper.getReadableDatabase()) {

            rowsCount = (int) DatabaseUtils.queryNumEntries(
                    db, tableName, null);
        } catch (Exception e) {
            Log.e(TAG,"Unable to count table rows " + e.toString());
        }

        return rowsCount;
    }

    public static int longForQuery(Context context,
                                   String query) {
        int rowsCount = -1;
        SQLiteOpenHelper mOpenHelper = QuotesProvider.createDBOpenHelper(context);
        try(SQLiteDatabase db = mOpenHelper.getReadableDatabase()) {

            rowsCount = (int) DatabaseUtils.longForQuery(
                    db, query, null);
        } catch (Exception e) {
            Log.e(TAG,"Unable to count table rows " + e.toString());
        }

        return rowsCount;
    }
}