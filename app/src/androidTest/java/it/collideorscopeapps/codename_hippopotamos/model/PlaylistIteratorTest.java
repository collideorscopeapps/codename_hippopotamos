package it.collideorscopeapps.codename_hippopotamos.model;

import android.content.Context;
import android.util.Log;

import androidx.test.filters.Suppress;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.DBManager;

import static org.junit.Assert.*;

public class PlaylistIteratorTest {

    @Test
    public void navigateAllScreens() {

        // TODO handle the fact that a screen can be repeated in more playlists, so the move count increases
        // TODO handle the fact that some playlists may be disables, so the moves count might decrease
        // TODO (implement in iterator and quote activity option to skip disabled screens or not)

        // get an iterator, gets from first screen to last and then back to first
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBManager dbManager = new DBManager(appContext);

        TreeMap<Integer, Schermata> schermateById;
        ArrayList<Playlist> playlists;
        PlaylistIterator plItr;
        schermateById = dbManager.getSchermateById(DBManager.Languages.EN);
        playlists = dbManager.getPlaylists();

        int totalPlaylists = playlists.size();
        int disabledPlaylistsCount = 0;
        int screensAppearancesInDisabledPlaylistsCount = 0;
        int viewAbleScreensRepetitionsCount = 0;
        int totalViewableScreensAppearancesCount = 0;
        TreeMap<Integer, Integer> screensAppearencesCount = new TreeMap<>();//first id is screen id, second is count
        for(Playlist pl:playlists) {
            TreeMap<Integer, Schermata> rankedSchermate = pl.getRankedSchermate();

            if(pl.isDisabled()) {
                disabledPlaylistsCount++;
                screensAppearancesInDisabledPlaylistsCount += pl.getRankedSchermate().size();
                //NB: we are counting screens in disabled playlists,
                // but we are not checking if these are repetitions,
                //that is, if these screens appear also in other playlists, enabled or not
            }
            else {
                totalViewableScreensAppearancesCount += rankedSchermate.values().size();

                for(Schermata schermata:rankedSchermate.values()) {
                    int newCountForThisScreen = 1;
                    if(screensAppearencesCount.containsKey(schermata.getId())) {
                        newCountForThisScreen = screensAppearencesCount.get(schermata.getId());
                        viewAbleScreensRepetitionsCount++;
                    }

                    screensAppearencesCount.put(schermata.getId(), newCountForThisScreen);
                }
            }
        }

        plItr = new PlaylistIterator(schermateById, playlists);

        //TODO check if any screen is in no playlist
        int totalUniqueScreens = schermateById.size();
        int expectedMoves = totalViewableScreensAppearancesCount;

        Log.d("PlaylistIteratorTest","Iterating screens forward");
        int forwardMovesCount = 0;
        while(plItr.hasNextScreen()) {
            Schermata screen = plItr.getNextScreen();
            forwardMovesCount++;

            Log.d("PlaylistIteratorTest", "(" + forwardMovesCount + ") " + screen.toString());
        }

        Log.d("PlaylistIteratorTest","Iterating screens backward");
        int backwardMovesCount = 0;
        while(plItr.hasPrevScreen()) {
            Schermata screen = plItr.getPrevScreen();
            backwardMovesCount++;

            Log.d("PlaylistIteratorTest", "("
                    + (forwardMovesCount - backwardMovesCount)
                    + ") " + screen.toString());
        }

        String errorMsg = "Expected " + expectedMoves
                + ", found " + forwardMovesCount + " (fwd), "
                + backwardMovesCount + " (bwd); "
                + "unique screens: " + totalUniqueScreens
                + "; enabled screens repetitions: " +  viewAbleScreensRepetitionsCount
                + "; disabled screens appearances: " + screensAppearancesInDisabledPlaylistsCount
                + "; total playlists " + totalPlaylists
                + "; disabled playlists " + disabledPlaylistsCount;

        assertTrue(errorMsg, expectedMoves == forwardMovesCount);
        assertTrue(errorMsg, expectedMoves-1 == backwardMovesCount);
    }

    @Test@Suppress
    public void getCurrentScreen() {
    }

    @Test@Suppress
    public void hasNextScreen() {
    }

    @Test@Suppress
    public void getNextScreen() {
    }

    @Test@Suppress
    public void hasPrevScreen() {
    }

    @Test@Suppress
    public void getPrevScreen() {
    }
}