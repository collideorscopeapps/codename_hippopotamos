package it.collideorscopeapps.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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

    //TODO add localization and screenshots, to give a glance
    // at the app in pictures https://gitlab.com/fdroid/fdroiddata/blob/master/CONTRIBUTING.md
    // https://f-droid.org/en/docs/All_About_Descriptions_Graphics_and_Screenshots/

    TextView titleTV,
            greekShortTV,
            greekLongTV,
            citationTV,
            phoneticsTV,
            translationTV,
            lingNotesTV,
            eeCTV;
    FrameLayout playbackButtonsFL;
    //EditText addressET;
    //ImageView imageIV;

    TreeMap<Integer, Schermata> schermateById;
    TreeMap<Integer,Playlist> playlists;
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
        this.greekShortTV = findViewById(R.id.greekShortTextTV);
        this.greekLongTV = findViewById(R.id.greekLongTextTV);
        this.titleTV = findViewById(R.id.titleTV);
        this.citationTV = findViewById(R.id.citationRefTV);
        this.eeCTV = findViewById(R.id.eeCommentTV);
        this.lingNotesTV = findViewById(R.id.linguisticNoteTV);
        this.phoneticsTV = findViewById(R.id.phoneticsTV);
        this.translationTV = findViewById(R.id.translationTV);
        this.playbackButtonsFL = findViewById(R.id.playbackButtons);

        this.playbackButtonsFL.setVisibility(View.GONE);
        this.phoneticsTV.setVisibility(View.GONE);

        //TODO fix title not showing, fix translations not showing, fix playlists not playing

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

        //TODO
        // (unsure, because of user interface psychological effects, also I like the bare unmarked buttons)
        // remove buttons, only use swipes, and click on greek text to play it again

        //FIXME screen glitch on displaying the soft aspiration
        // also ῆ not displayed
        // on android 4.4.2 also ῖ not showing (all circumflex letters?)
        // try changing font
        //FIXME
        // title not displayed
        //FIXME
        // some playlits actually not shown in app (mythic quotes)
        //TODO
        // add option to disable autoplay, might be annoying when swiping quickly many quotes
        // (or disable it if two swipes in quick succession
        // better: don't start it immediately, give the user time to swipe. if he doesn't, start playing
        //TODO
        // add credit, wiki page with aika quote
        //TODO use new AudioPlayerHelper
        //TODO
        // release of mp with AudioPlayerHelper.close, would be only
        // when quitting activity
        // not when changing to other screen with other audio quotes

        //TODO ..might prepare in advance file descriptors for next screen

        //TODO: tweak quoteactivity to load and play the audio file if present
        //TODO: add check in quote activity, if file present, show toad if not
        //TODO: should the audio player throw exception or return message/boolean?

        //FIXME increase volume

        //TODO add counter in bottom left corner to know in which screen / playlist we are
        // i.e. 1 / 40
    }

    private void setLeftRightSwipeBehavior() {

        //TODO add swipe animation

        //View rootView = this.getCurrentFocus().getRootView();
        View rootView = findViewById(R.id.greekShortTextTV).getRootView();

        onSwipeTouchListener = new OnSwipeTouchListener(
                QuoteActivity.this) {
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

        //TODO set defaults for "(this word is untranslatable)"
        // set a screen where is displayed
        // with doric, epic, ionic and attic
        // some preview/tutorial screen? ..

        this.titleTV.setText(screen.getTitle());

        //TODO
        // populate UI widgets with data for current schermata
        // load screen data into the TV, etc
        // set also the audio player
        // log error message when audio file not found
        setGreekTV(this.greekShortTV, screen.getShortQuote());
        setGreekTV(this.greekLongTV, screen.getFullQuote());

        //FIXME db gets not refreshed after changes and new run

        //TODO FIXME this.phoneticsTV.setText(screen.);
        //this.phoneticsTV = findViewById(R.id.phoneticsTV);

        this.citationTV.setText(screen.getCitation());
        this.translationTV.setText(screen.getTranslation());
        this.eeCTV.setText(screen.getEasterEggComment());
        this.lingNotesTV.setText(screen.getLinguisticNotes());
    }

    private static void setGreekTV(TextView tv, Quote quote) {
        final MyHtmlTagHandler htmlTagHandler
                = new MyHtmlTagHandler();


        //TODO (not in this method)
        // update previous quotes to show in new short/long quote format

        if(quote == null) {
            Log.e("QuoteActivity","Null quote passed.");
            tv.setText("");
        }
        else {
            String quoteTxt = quote.getQuoteText();
            tv.setText(Html.fromHtml(quoteTxt,
                    null,
                    htmlTagHandler));
        }
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
