package it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.DBManager;
import it.collideorscopeapps.codename_hippopotamos.model.Playlist;
import it.collideorscopeapps.codename_hippopotamos.model.PlaylistIterator;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class ScreenSlidePagerViewModel extends AndroidViewModel {
    // WIP: Implement the ViewModel

    TreeMap<Integer, Schermata> schermateById;
    TreeMap<Integer, Playlist> playlists;
    PlaylistIterator plItr;
    DBManager dbMng;

    public ScreenSlidePagerViewModel(Application application) {
        super(application);
        dbMng = new DBManager(application);
    }

    private TreeMap<Integer, Schermata> getSchermateById() {
        if (schermateById == null) {

            //TODO FIXME retrieve language settings from shared preferences
            this.schermateById = dbMng.getSchermateById(DBManager.Languages.EN);
            this.playlists = dbMng.getPlaylists();

        }
        return this.schermateById;
    }

    public int getScreenCount() {
        //FIXME do a more precise count iterating playlists
        return this.getSchermateById().size();
    }

    public Schermata getScreen(int screenId) {
        return this.getSchermateById().get(screenId);
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}
