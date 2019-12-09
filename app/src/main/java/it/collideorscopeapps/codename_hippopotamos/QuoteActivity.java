package it.collideorscopeapps.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

        playAudioFile(mediaPlayer, assetManager, audioFilePath);

    }

    private static void insertFileIntoMediaplayer(MediaPlayer mediaPlayer,
                                                  AssetFileDescriptor assetFileDescriptor)
            throws IOException {
        if(android.os.Build.VERSION.SDK_INT >= 24) {
            mediaPlayer.setDataSource(assetFileDescriptor);
        }
        else {
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            long offset = assetFileDescriptor.getStartOffset();
            long length = assetFileDescriptor.getLength();
            mediaPlayer.setDataSource(
                    fileDescriptor,
                    offset,
                    length);
        }
    }

    private static void playAudioFile(MediaPlayer mediaPlayer,
                                      AssetManager assetManager,
                                      String audioFilePath) {

        try(AssetFileDescriptor assetFileDescriptor = assetManager.openFd(audioFilePath)) {

            insertFileIntoMediaplayer(mediaPlayer, assetFileDescriptor);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer player) {
                    Log.v("QuoteActivity","Starting media player..");
                    player.start();
                    //TODO should we ensure playback is completed before relase? is .start asynch?
                    //player.release();
                    //player = null;
                }
            });

            mediaPlayer.prepareAsync();
        }
        catch (IOException e) {
            Log.e("QuoteActivity-audioplay", e.toString());
        }
    }

}
