
package it.collideorscopeapps.codename_hippopotamos.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class PlaylistIterator {

    TreeMap<Integer, Schermata> schermateById;
    ArrayList<Playlist> playlists;
    ListIterator<Playlist> playlistIterator;
    static final boolean DEFAULT_SKIP_DISABLED_PLAYLISTS = true;
    boolean skipDisabledPlaylists;

    enum Move {
        SET_TO_FIRST,
        FW,
        BW
    }
    Move lastPlaylistSwitchMove;

    Playlist currentPl;
    TreeMap<Integer, Schermata> currentRankedSchermate;
    int currentScreenRank;
    Schermata currentScreen;

    boolean notMovedYet = true;

    public PlaylistIterator(TreeMap<Integer, Schermata> schermateById,
                            ArrayList<Playlist> playlists) {
        this(schermateById, playlists, DEFAULT_SKIP_DISABLED_PLAYLISTS);
    }

    public PlaylistIterator(TreeMap<Integer, Schermata> schermateById,
                            ArrayList<Playlist> playlists,
                            boolean skipDisabledPlaylists) {

        this.schermateById = schermateById;

        this.playlists = playlists;
        this.skipDisabledPlaylists = skipDisabledPlaylists;
        if(skipDisabledPlaylists) {
            ArrayList<Playlist> enabledPlaylists = new ArrayList<>();
            for (Playlist pl : playlists) {
                if (!pl.isDisabled()) {
                    enabledPlaylists.add(pl);
                    Log.v("PlaylistIterator","Added one enabled playlist");
                }
                else {
                    Log.v("PlaylistIterator","Skipped one disabled playlist");
                }
            }
            this.playlists = enabledPlaylists;
            Log.v("PlaylistIterator","Total playlists: " + playlists.size());
            Log.v("PlaylistIterator","Using " + this.playlists.size() + " playlists");
        }

        this.playlistIterator = this.playlists.listIterator();

        this.currentPl = this.playlistIterator.next();
        this.lastPlaylistSwitchMove = Move.SET_TO_FIRST;
        Log.v("PlaylistIterator","Starting with playlist " + currentPl.getDescription());

        this.currentRankedSchermate = this.currentPl.getRankedSchermate();
    }

    public Schermata getCurrentScreen() {
        if(this.notMovedYet) {
            return this.getNextScreen();
        }

        return this.currentScreen;
    }

    public boolean hasNextScreen() {
        if(this.notMovedYet) {
            // todo limit case: there are no screens,
            // all playlists are empty or there are no playlists
            return true;
        }

        if (currentRankedSchermate.lastKey() != this.currentScreenRank) {
            return true;
        }

        return this.playlistIterator.hasNext();

        //other limit (unlikely) scenario: the last playlist has no screen
    }

    public Schermata getNextScreen() {

        if(this.notMovedYet) {

            Map.Entry<Integer, Schermata> currentScreenEntry = this.currentRankedSchermate.firstEntry();
            this.currentScreen = currentScreenEntry.getValue();
            this.currentScreenRank = currentScreenEntry.getKey();

            this.notMovedYet = false;
            return this.currentScreen;

        }

        Map.Entry<Integer, Schermata> nextScreenEntry;

        if(currentRankedSchermate.lastKey() != this.currentScreenRank) {

            nextScreenEntry = this.currentRankedSchermate.higherEntry(this.currentScreenRank);

        } else {

            if(this.lastPlaylistSwitchMove == Move.BW) {
                // when we go back and forth in a ListIterator, we should skip one
                // because it returns twice the same
                this.playlistIterator.next();
            }

            if(this.playlistIterator.hasNext()) {

                this.currentPl = this.playlistIterator.next();
                this.lastPlaylistSwitchMove = Move.FW;
                Log.v("PlaylistIterator","Moving fw to playlist " + currentPl.getDescription());
                this.currentRankedSchermate = this.currentPl.getRankedSchermate();

                nextScreenEntry = this.currentRankedSchermate.firstEntry();
            } else {
                // Exception, should have checked hasNext() first
                throw new NoSuchElementException();//todo print current playlist, rank, and screen
            }
        }

        return nextScreen(nextScreenEntry);
    }

    private Schermata nextScreen(Map.Entry<Integer, Schermata> nextScreenEntry) {
        currentScreenRank = nextScreenEntry.getKey();
        currentScreen = nextScreenEntry.getValue();

        return this.currentScreen;
    }

    public boolean hasPrevScreen() {
        if(this.notMovedYet) {
            // currently cannot go back as a first move
            return false;
        }

        if (!weAreAtFirstScreenOfPlaylist()) {
            return true;
        }

        return this.playlistIterator.hasPrevious();

        //other limit (unlikely) scenario: the first playlist has no screens
    }

    private boolean weAreAtFirstScreenOfPlaylist() {

        return currentRankedSchermate.firstKey() == this.currentScreenRank;
    }

    public Schermata getPrevScreen() {

        if(this.notMovedYet) {
            // currently cannot go back as a first move
            throw new NoSuchElementException();
        }

        Map.Entry<Integer, Schermata> prevScreenEntry;

        if (!weAreAtFirstScreenOfPlaylist()) {

            prevScreenEntry = this.currentRankedSchermate.lowerEntry(this.currentScreenRank);

        } else {

            if(this.lastPlaylistSwitchMove == Move.FW) {
                // when we go back and forth in a ListIterator, we should skip one
                // because it returns twice the same
                this.playlistIterator.previous();
            }

            if(this.playlistIterator.hasPrevious()) {

                this.currentPl = this.playlistIterator.previous();
                this.lastPlaylistSwitchMove = Move.BW;
                Log.v("PlaylistIterator","Moving bw to playlist " + currentPl.getDescription());
                this.currentRankedSchermate = this.currentPl.getRankedSchermate();

                prevScreenEntry = this.currentRankedSchermate.lastEntry();
            } else {
                // Exception, should have checked hasPrevious()
                throw new NoSuchElementException();//todo print current playlist, rank, and screen
            }
        }

        return prevScreen(prevScreenEntry);
    }

    private Schermata prevScreen(Map.Entry<Integer, Schermata> prevScreenEntry) {
        currentScreenRank = prevScreenEntry.getKey();
        currentScreen = prevScreenEntry.getValue();

        return this.currentScreen;
    }

}