package it.collideorscopeapps.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import java.util.HashMap;

import it.collideorscopeapps.codename_hippopotamos.database.AudioPlayUtils;
import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class QuoteActivity extends AppCompatActivity {

    // todo, at activity startup, get schermata with "audio test"
    // iterate the quotes, get the audio files from assets folder
    // play the ogg vorbis files

    HashMap schermate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        schermate = (HashMap) getIntent().getExtras().get("schermate");

        // TODO get UI widgets to populate
        // populate UI widgets with data for current schermata
        // ..

        // set event listeners on some widgets (play button, back and forward buttons)
        // favourites button

        // poi lo scorrimento da una schermata all'altra è gestito dagli event listeners
        final int ID_SCHEMATA_AUDIO_TEST = 14;
        final int ID_SOME_QUOTE = 1;
        Schermata audio_test = (Schermata) schermate.get(ID_SCHEMATA_AUDIO_TEST);
        Quote some_quote = audio_test.getQuotes().get(ID_SOME_QUOTE);
        String audioFileName = some_quote.getAudioFileName();
        String audioFilesSubFolder = "audio/";
        String audioFilePath = audioFilesSubFolder + audioFileName;

        AssetManager assetManager = this.getAssets();
        MediaPlayer mediaPlayer = new MediaPlayer();

        AudioPlayUtils.playAudioFile(mediaPlayer, assetManager, audioFilePath);

    }
}
