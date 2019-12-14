package it.collideorscopeapps.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.AudioPlayerHelper;
import it.collideorscopeapps.codename_hippopotamos.database.DBManager;
import it.collideorscopeapps.codename_hippopotamos.model.Playlist;
import it.collideorscopeapps.codename_hippopotamos.model.PlaylistIterator;
import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class QuoteActivity extends AppCompatActivity {

    // todo, at activity startup, get schermata with "audio test"
    // iterate the quotes, get the audio files from assets folder
    // play the ogg vorbis files
    OnSwipeTouchListener onSwipeTouchListener;

    TextView greekTV, phoneticsTV, titleTV, translationTV, lingNotesTV, eeCTV;
    //EditText addressET;
    //ImageView imageIV;

    TreeMap<Integer, Schermata>  schermateById;
    ArrayList<Playlist> playlists;
    PlaylistIterator plItr;

    String currentAudioFilePath;
    AssetManager assetManager;
    AudioPlayerHelper audioPlayerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        DBManager dbMng = new DBManager(this);

        this.schermateById = dbMng.getSchermateById(DBManager.Languages.EN);
        this.playlists = dbMng.getPlaylists();

        // TODO get UI widgets to populate
        // ..
        // set event listeners on some widgets (play button, back and forward buttons)
        // add favourites button
        this.greekTV = findViewById(R.id.greekTextTV);

        Button playBtn = findViewById(R.id.playButton);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playCurrentFile();
            }
        });

        Button nextScreenBtn = findViewById(R.id.nextButton);
        nextScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        });

        Button prevScreenBtn = findViewById(R.id.backButton);
        prevScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        setLeftRightSwipeBehavior();

        //TODO handle activity getting closed by OS
        // saving the current screen, so can be reloaded at reopening
        // handle activity lifecycle
        // avoid activy replaying audio every time is app is reopened from background

        //TODO
        // keep current scermata in playlist
        this.plItr = new PlaylistIterator(this.schermateById, this.playlists);

        //TODO should this be an instance variable?
        Schermata currentScreen = this.plItr.getCurrentScreen();
        refreshToScreen(currentScreen);

        // hippopotamos tagline: you don't find such phrases among the Romans
        //(queste frasi tra i Romani non le trovate)
        //in latino non ce ne sono di queste frasi
        //queste parole i Romani non ce le avevano
        //queste frasi i Romani non ce le avevano
        //questa letteratura i Romani non ce l'avevano
        //queste lettere i Romani non ce le avevano (tagline changes at every startup in italian)
        // (switches randomly among multiple translations)
        //queste frasi in latino non ci sono
        //(queste frasi non ci sono in latino)
        // (poi ripetere la tagline solo come commento alla frase dei due grammar books)


        //FIXME screen glitch on displaying the soft aspiration
        // try changing font
        //TODO use new AudioPlayerHelper
        //TODO
        // release of mp with AudioPlayerHelper.close, would be only
        // when quitting activity
        // not when changing to other screen with other audio quotes

        //TODO ..might prepare in advance file descriptors for next screen
    }

    private void setLeftRightSwipeBehavior() {
        //View rootView = this.getCurrentFocus().getRootView();
        View rootView = findViewById(R.id.greekTextTV).getRootView();;

        onSwipeTouchListener = new OnSwipeTouchListener(QuoteActivity.this) {
            @Override
            public void onSwipeLeft() {
                goNext();
            }

            @Override
            public void onSwipeRight() {
                goBack();
            }
        };

        rootView.setOnTouchListener(onSwipeTouchListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void goNext() {
        Schermata nextScreen = plItr.getNextScreen();
        refreshToScreen(nextScreen);
    }

    private void goBack() {
        Schermata prevScreen = plItr.getPrevScreen();
        refreshToScreen(prevScreen);
    }

    private Schermata getCurrentSchermata() {
        return this.plItr.getCurrentScreen();
    }

    private void refreshToScreen(Schermata screen) {
        //TODO
        // populate UI widgets with data for current schermata
        // load screen data into the TV, etc
        // set also the audio player
        // log error message when audio file not found
        this.greekTV.setText(screen.getQuotesAsString());
    }

    @Deprecated
    private void loadAudioTestScreen() {
        final int ID_SCHEMATA_AUDIO_TEST = 14;
        final int ID_SOME_QUOTE = 1;
        Schermata audio_test = (Schermata) schermateById.get(ID_SCHEMATA_AUDIO_TEST);
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

    }

    private void playCurrentFile() {
        this.audioPlayerHelper.play();
        //AudioPlayUtils.playAudioFile(mediaPlayer, assetManager, currentAudioFilePath);
    }
}
