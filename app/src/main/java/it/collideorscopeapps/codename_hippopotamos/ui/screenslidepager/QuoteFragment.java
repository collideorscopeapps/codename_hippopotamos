package it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import it.collideorscopeapps.codename_hippopotamos.Globals;
import it.collideorscopeapps.codename_hippopotamos.utils.MyHtmlTagHandler;
import it.collideorscopeapps.codename_hippopotamos.R;
import it.collideorscopeapps.codename_hippopotamos.database.AudioPlayerHelper;
import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;
import it.collideorscopeapps.codename_hippopotamos.utils.Utils;

public class QuoteFragment extends Fragment {

    public static final String TAG = "QuoteFragment";
    public static final String SCREEN_ID_BUNDLE_FIELD = "screenId";

    static final String DEFAULT_TITLE_TEXT
            = "";

    AssetManager assetManager;
    AudioPlayerHelper audioPlayer;
    MyHtmlTagHandler htmlTagHandler;

    int position;
    int screensCount;
    Schermata screen;

    String shortQuoteAudioAssetPath;
    String longQuoteAudioAssetPath;
    String[] audioAssetsPaths;

    TextView titleTV,
            greekShortTV,
            greekLongTV,
            greekWordListTV,
            citationTV,
            phoneticsTV,
            translationTV,
            lingNotesTV,
            eeCTV,
            pageCounterTV;

    public QuoteFragment(int position, Schermata screen, int screensCount,
                         AudioPlayerHelper audioPlayer,
                         AssetManager assetManager) {
        this.htmlTagHandler = new MyHtmlTagHandler();

        this.screensCount = screensCount;
        this.position = position;
        this.screen = screen;
        this.audioPlayer = audioPlayer;
        this.assetManager = assetManager;

        initAudioAssetsPaths();
    }

    public static QuoteFragment newInstance(int position, Schermata screen,
                                            int screensCount,
                                            AudioPlayerHelper audioPlayer,
                                            AssetManager assetManager) {
        return new QuoteFragment(position, screen, screensCount,
                audioPlayer,
                assetManager);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //The system calls this when creating the fragment.
        // Within your implementation, you should initialize
        // essential components of the fragment that you want
        // to retain when the fragment is paused or stopped, then resumed.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_slide_pager_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.greekShortTV = view.findViewById(R.id.greekShortTextTV);
        this.greekLongTV = view.findViewById(R.id.greekLongTextTV);
        this.greekWordListTV = view.findViewById(R.id.greekWordListTV);
        this.titleTV = view.findViewById(R.id.titleTV);
        this.pageCounterTV = view.findViewById(R.id.pageCounterTV);
        this.citationTV = view.findViewById(R.id.citationRefTV);
        this.eeCTV = view.findViewById(R.id.eeCommentTV);
        this.lingNotesTV = view.findViewById(R.id.linguisticNoteTV);
        this.phoneticsTV = view.findViewById(R.id.phoneticsTV);
        this.translationTV = view.findViewById(R.id.translationTV);

        this.phoneticsTV.setVisibility(View.GONE);

        ensureTypeface(view.getContext());

        //TODO delay setting filenames to audioplayer
        // TODO implement playing long quote after short quote
        //  by setting both in media player changeAudioFiles

        //TODO consider implementing for when series of quote files
        // (i.e. screen with word list instead of quote
        //ArrayList<String> audioFilePathsNames
        //        = Globals.getAudioFilePathNames(assetManager, screen);

        this.greekShortTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(audioPlayer.isPlayingOrPaused()) {
                    audioPlayer.pauseOrResume();
                } else {
                    playShortQuote();
                }
            }
        });

        this.greekLongTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(audioPlayer.isPlayingOrPaused()) {
                    audioPlayer.pauseOrResume();
                } else {
                    playLongQuote();
                }
            }
        });

        this.greekWordListTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audioPlayer.isPlayingOrPaused()) {
                    audioPlayer.pauseOrResume();
                } else {
                    playWordList();
                }
            }
        });

        //TODO
        //(2)second type of screen: disambiguation
        //this could have multiple quotes
        //a .."title" with the ending to disanmbiguate

        //(1)adjective screen should be like noun declension screens
        //(3)then prepositions/conjunctions, (4)verbs, (5)adverbs
        //(6)pronouns
        //(7)then longer quotes with notes
        //(8) and recap screens (i.e. for noun declensions)
        // type I: single quote, with shorter and longer version
        // i.e. for a GEN pl ending, and detailed linguistic notes
        // type II: recap/multiple quotes
        // on the audio side, typeII could be done using the longer quote
        // all in one audio file

        //TODO on Main screen choices, "Demo", to play some screens that have audio
        // ..
        // then settings, about, credits, ..list of screens, favourites, (history..)
        // resume from last
        // longplay mode
        // review/study mode
    }

    @Override
    public void onStart() {
        super.onStart();

        //NB moved code to play file here (stop previous play,
        // autoplay this fragment files
        // this was because onStart() is called when the user begins
        // to scroll the page and this fragments begins to partially apper
        // but the transition is still not complete and the current fragment
        // is still the previous one (and the transition could be canceled)
        // the playback of new files should start when the transition is
        // completed.
        // however, with viewpager2, there seems to be no straight forward
        // way to notify the fragment when it has become the current one.
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        //The system calls this method as the first indication
        // that the user is leaving the fragment (though it
        // doesn't always mean the fragment is being destroyed).
        // This is usually where you should commit any changes
        // that should be persisted beyond the current user
        // session (because the user might not come back).
        this.audioPlayer.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        //this.audioPlayer.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void ensureTypeface(Context context) {
        Typeface prefTypeface = Globals.getPreferredTypeface(
                context);
        this.greekShortTV.setTypeface(prefTypeface);
        this.greekLongTV.setTypeface(prefTypeface);
        this.greekWordListTV.setTypeface(prefTypeface);
        this.lingNotesTV.setTypeface(prefTypeface);
        this.titleTV.setTypeface(prefTypeface);
        this.pageCounterTV.setTypeface(prefTypeface);
        this.translationTV.setTypeface(prefTypeface);
    }

    void playWordList() {
        AudioPlayerHelper.playQuotes(this.audioAssetsPaths,this.audioPlayer);
    }

    private void initAudioAssetsPaths(){
        this.shortQuoteAudioAssetPath
                = Globals.getAudioAssetPath(assetManager,screen.getShortQuote());
        this.longQuoteAudioAssetPath
                = Globals.getAudioAssetPath(assetManager,screen.getFullQuote());

        this.audioAssetsPaths = screen.getAudioAssetsPaths(assetManager);
    }

    static void playQuote(String quoteAssetPath, AudioPlayerHelper player) {

        if(Utils.isNullOrEmpty(quoteAssetPath)) {
            Log.e(TAG,"No quote, can't play");
            return;
        }

        AudioPlayerHelper.playQuotes(new String[]{quoteAssetPath}, player);
    }

    void playShortQuote() {
        playQuote(this.shortQuoteAudioAssetPath, this.audioPlayer);
    }

    void playLongQuote() {
        playQuote(this.longQuoteAudioAssetPath, this.audioPlayer);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ViewModelProvider viewModelProvider = ViewModelProviders.of(this);
        //mViewModel = viewModelProvider.get(QuoteViewModel.class);
        // TODO: Use the ViewModel

        //TODO check if we actually need to use Bundle savedInstanceState in some way

        // if (savedInstanceState != null) { ..}
        // Restore last state for current screen
        //this.screenId = savedInstanceState.getInt(SCREEN_ID_BUNDLE_FIELD);
        // else Log.e(TAG,"Null saved state");

        loadWidgets();

        //TODO, get screen data from ViewModel
        //Schermata screen = mViewModel.getScreen(this.screenId);

        //TODO slidePagerActivity must someone put the page position
        // (corrensponding activity id), in the Bundle savedInstanceState

    }

    private void loadWidgets() {

        //TODO set defaults for "(this word is untranslatable)"
        // set a screen where is displayed
        // with doric, epic, ionic and attic
        // some preview/tutorial screen? ..

        setTextToTitleTV();

        String pageCount = (this.position + 1)
                + " of " + this.screensCount;
        this.pageCounterTV.setText(pageCount);

        //TODO
        // populate UI widgets with data for current schermata
        // load screen data into the TV, etc
        // set also the audio player
        // log error message when audio file not found
        setGreekTV(this.greekShortTV, screen.getShortQuote());
        setGreekTV(this.greekLongTV, screen.getFullQuote());
        setWordListTV(this.greekWordListTV, screen);
        // TODO set a default text message if all three are emtpty?
        setVisibilityOfGreekTextViews();

        //FIXME db gets not refreshed after changes and new run

        //TODO FIXME this.phoneticsTV.setText(screen.);
        //this.phoneticsTV = findViewById(R.id.phoneticsTV);

        this.citationTV.setText(screen.getCitation());
        this.translationTV.setText(screen.getTranslation());
        this.eeCTV.setText(screen.getEasterEggComment());
        this.lingNotesTV.setText(screen.getLinguisticNotes());
    }

    private void setTextToTitleTV() {

        final boolean useDescriptionAsTitle = false;

        String title = screen.getTitle();

        if(useDescriptionAsTitle && Utils.isNullOrEmpty(title)) {
            title = screen.getDescription();
        }

        if(Utils.isNullOrEmpty(title)) {
            title = DEFAULT_TITLE_TEXT;
        }

        this.titleTV.setText(title);
    }

    private void setVisibilityOfGreekTextViews() {

        this.greekWordListTV.setVisibility(View.GONE);

        if(Utils.isNullOrEmpty(screen.getFullQuote())) {

            this.greekLongTV.setVisibility(View.GONE);

            setSingleGreekTVPadding(this.greekShortTV);

            if(Utils.isNullOrEmpty(screen.getShortQuote())) {

                this.greekShortTV.setVisibility(View.GONE);
                this.greekWordListTV.setVisibility(View.VISIBLE);
                setSingleGreekTVPadding(this.greekWordListTV);

                //TODO if no quotes or word list, show some default message
            }
        }
    }

    private static void setSingleGreekTVPadding(TextView tv) {
        int newPaddingTop = 32 + tv.getPaddingTop();

        int paddingBottom = tv.getPaddingBottom();
        int paddingLeft = tv.getPaddingLeft();
        int paddingRight = tv.getPaddingRight();

        tv.setPadding(paddingLeft,newPaddingTop,
                paddingRight,paddingBottom);
    }

    private void setGreekTV(TextView tv, Quote quote) {
        //TODO (not in this method)
        // update previous quotes to show in new short/long quote format

        if(quote == null) {
            Log.e(TAG,"Null quote passed.");
            tv.setText("");
        }
        else {
            String quoteTxt = quote.getQuoteText();
            setHtmlText(tv, quoteTxt);
        }
    }

    private void setHtmlText(TextView tv, String htmlText) {
        tv.setText(Html.fromHtml(htmlText,
                null,
                htmlTagHandler));
    }

    private void setWordListTV(TextView tv, Schermata screen) {

        setHtmlText(tv, screen.getMultilineHtmlWordList());

        //TODO in other part of code, set playback to list of words
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(SCREEN_ID_BUNDLE_FIELD, this.screenId);
    }
}
