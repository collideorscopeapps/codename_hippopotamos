package it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.DBManager;
import it.collideorscopeapps.codename_hippopotamos.model.Playlist;
import it.collideorscopeapps.codename_hippopotamos.model.PlaylistIterator;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class QuoteViewModel extends AndroidViewModel {
    // WIP: Implement the ViewModel

    TreeMap<Integer, Schermata> schermateById;
    TreeMap<Integer, Playlist> playlists;
    PlaylistIterator plItr;
    DBManager dbMng;

    public QuoteViewModel(Application application) {
        super(application);
        dbMng = new DBManager(application);

        init();
    }

    private TreeMap<Integer, Schermata> getSchermateById() {

        return this.schermateById;
    }

    private void init() {

        //TODO FIXME retrieve language settings from shared preferences
        this.schermateById = dbMng.getSchermateById(DBManager.Languages.EN);
        this.playlists = dbMng.getPlaylists();
        //TODO
        // keep current schermata in playlist
        this.plItr = new PlaylistIterator(this.schermateById, this.playlists);
    }

    public int getScreenCount() {

        return this.plItr.screensCount();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        this.dbMng.close();
    }

    /*public Schermata getScreen(int screenId) {
        return this.getSchermateById().get(screenId);
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }*/
}
