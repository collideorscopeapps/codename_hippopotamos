package it.collideorscopeapps.codename_hippopotamos;

import org.junit.Test;
import org.junit.Assert;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.DBManager;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

//import static com.google.common.truth.Truth.assertThat;
//import com.google.common.truth.Truth.assertThat;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestUtils {

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

}