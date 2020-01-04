package it.collideorscopeapps.codename_hippopotamos;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.QuotesProvider;
import it.collideorscopeapps.codename_hippopotamos.model.Playlist;

public class PlaylistsActivity extends ListActivity {

    /* TODO
    * fleuron at the top
    * top margin/padding
    * list of playlists
    * letters as playlist numberals on the left
    *
    * underline the last clicked playlist
     * */

    List<Map<String, String>> playlistsNamesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        //startManagingCursor();

        final String[] columns = {QuotePagerActivity.PLAYLIST_NUMBER_EXTRA_KEY,
                QuotePagerActivity.PLAYLIST_NAME_EXTRA_KEY};
        final int[] textViews = {R.id.playlistNumber, R.id.playlistName};
        this.playlistsNamesData = getData();
        //Each entry should be.. a playlsit name, title or description
        //Map entry example: playlistNumber -> alpha, playlistName -> prepositions

        SimpleAdapter adapter = new SimpleAdapter(this,playlistsNamesData,
                R.layout.playlist_item,columns,textViews);

        this.setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //TODO set text style of current item as underlined
        // save the position of the previous selected item
        // and set text to normal

        //TODO check that numbering of position starts at 0, so it's same as ArrayList index
        Map<String, String> playlistData = this.playlistsNamesData.get(position);
        String playlistName = playlistData.get(QuotePagerActivity.PLAYLIST_NAME_EXTRA_KEY);

        Intent intent = new Intent(PlaylistsActivity.this,
                QuotePagerActivity.class);
        intent.putExtra(QuotePagerActivity.PLAYLIST_NAME_EXTRA_KEY, playlistName);
        intent.setAction(QuotePagerActivity.PLAY_ACTION);
        startActivity(intent);
    }

    List<Map<String, String>> getData() {

        //TODO getPlaylists from model
        QuotesProvider quotesProvider = new QuotesProvider();
        quotesProvider.create(this);
        quotesProvider.init(QuotesProvider.DEFAULT_LANGUAGE, null);
        //this.schermateById = quotesProvider.getSchermateById();
        TreeMap<Integer,Playlist> playlistByRank = quotesProvider.getPlaylistsByRank();
        //this.plItr = new PlaylistIterator(this.schermateById, this.playlists);

        List<Map<String, String>> data = new ArrayList<>();

        int currentPlaylistNumber = 1;
        //loop on all playlist, as code in class ..
        //for each playlist:
        for(Playlist currentPlaylist:playlistByRank.values()) {

            String playListGreekNumber = parseGreekNumeral(currentPlaylistNumber);
            String playlistName = currentPlaylist.getDescription();

            Map<String, String> currentPlaylistEntry = new TreeMap<>();
            currentPlaylistEntry.put("playlistNumber",playListGreekNumber);
            currentPlaylistEntry.put("playlistName",playlistName);

            data.add(currentPlaylistEntry);
        }

        return data;
    }

    String parseGreekNumeral(int number) {

        String greekNumeralsString = "αβγδ..";
        char[] greekNumerals = greekNumeralsString.toCharArray();

        if(number > greekNumerals.length) {
            return null;
        }

        return String.valueOf(greekNumerals[number - 1]);
    }
}
