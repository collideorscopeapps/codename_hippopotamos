package it.collideorscopeapps.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.AudioPlayerHelper;
import it.collideorscopeapps.codename_hippopotamos.database.DBManager;
import it.collideorscopeapps.codename_hippopotamos.model.Playlist;
import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class QuoteActivity extends AppCompatActivity {

    // todo, at activity startup, get schermata with "audio test"
    // iterate the quotes, get the audio files from assets folder
    // play the ogg vorbis files

    TreeMap<Integer, Schermata>  schermate;
    ArrayList<Playlist> playlists;
    String currentAudioFilePath;
    AssetManager assetManager;
    AudioPlayerHelper audioPlayerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        DBManager dbMng = new DBManager(this);

        this.schermate = dbMng.getSchermate(DBManager.Languages.EN);
        this.playlists = dbMng.getPlaylists();

        // TODO get UI widgets to populate
        // populate UI widgets with data for current schermata
        // ..
        // set event listeners on some widgets (play button, back and forward buttons)
        // add favourites button
        Button playBtn = findViewById(R.id.playButton);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playCurrentFile();
            }
        });

        //TODO
        // load playlists
        // start from the first one, load it into the TV, etc
        // set also the audio player
        // log error message when audio file not found
        // keep current scermata in playlist (implement possibility to go back
        // (iterators?)

        // poi lo scorrimento da una schermata all'altra è gestito dagli event listeners
        final int ID_SCHEMATA_AUDIO_TEST = 14;
        final int ID_SOME_QUOTE = 1;
        Schermata audio_test = (Schermata) schermate.get(ID_SCHEMATA_AUDIO_TEST);
        Quote some_quote = audio_test.getQuotes().get(ID_SOME_QUOTE);
        String audioFileName = some_quote.getAudioFileName();
        String audioFilesSubFolder = "audio/";
        this.currentAudioFilePath = audioFilesSubFolder + audioFileName;
        this.assetManager = this.getAssets();

        String singleAudioFileName = "hhmeraf.ogg";
        String[] audioFileNames = new String[]{"xwraf.ogg", "logosf.ogg", "nomosf.ogg"};
        String[] audioFilePathsNames = new String[audioFileNames.length];
        String singleAudioFilePath = audioFilesSubFolder + singleAudioFileName;

        int idx = 0;
        for(String fileName:audioFileNames) {
            audioFilePathsNames[idx] = audioFilesSubFolder + fileName;
            idx++;
        }

        //TODO handle event onactivity end to release media player
        try {
            this.audioPlayerHelper = new AudioPlayerHelper(
                    assetManager, audioFilePathsNames);
            this.audioPlayerHelper.play();
        } catch (IOException e) {
            Log.e("QuoteActivity",e.toString());
        }

        //this.mediaPlayer = AudioPlayUtils.setUpMediaPlayer();
        //AudioPlayUtils.playAudioFile(mediaPlayer, assetManager, currentAudioFilePath);

        //TODO use new AudioPlayerHelper
        //TODO
        // release of mp with AudioPlayerHelper.close, would be only
        // when quitting activity
        // not when changing to other screen with other audio quotes

        //TODO ..might prepare in advance file descriptors for next screen
    }

    private void playCurrentFile() {
        this.audioPlayerHelper.play();
        //AudioPlayUtils.playAudioFile(mediaPlayer, assetManager, currentAudioFilePath);
    }
}
