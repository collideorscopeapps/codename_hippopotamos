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
import it.collideorscopeapps.codename_hippopotamos.MyHtmlTagHandler;
import it.collideorscopeapps.codename_hippopotamos.R;
import it.collideorscopeapps.codename_hippopotamos.database.AudioPlayerHelper;
import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;
import it.collideorscopeapps.codename_hippopotamos.utils.Utils;

public class QuoteFragment extends Fragment {

    public static final String TAG = "QuoteFragment";
    public static final String SCREEN_ID_BUNDLE_FIELD = "screenId";

    AssetManager assetManager;
    AudioPlayerHelper audioPlayer;

    int position;
    int screensCount;
    Schermata screen;

    String shortQuoteAudioAssetPath;
    String longQuoteAudioAssetPath;

    TextView titleTV,
            greekShortTV,
            greekLongTV,
            citationTV,
            phoneticsTV,
            translationTV,
            lingNotesTV,
            eeCTV;

    public QuoteFragment(int position, Schermata screen, int screensCount,
                         AudioPlayerHelper audioPlayer,
                         AssetManager assetManager) {
        this.screensCount = screensCount;
        this.position = position;
        this.screen = screen;
        this.audioPlayer = audioPlayer;
        this.assetManager = assetManager;

        this.shortQuoteAudioAssetPath
                = Globals.getAudioAssetPath(assetManager,screen.getShortQuote());
        this.longQuoteAudioAssetPath
                = Globals.getAudioAssetPath(assetManager,screen.getFullQuote());
    }

    public static QuoteFragment newInstance(int position, Schermata screen,
                                            int screensCount,
                                            AudioPlayerHelper audioPlayer,
                                            AssetManager assetManager) {
        return new QuoteFragment(position, screen, screensCount,
                audioPlayer,
                assetManager);
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
        this.titleTV = view.findViewById(R.id.titleTV);
        this.citationTV = view.findViewById(R.id.citationRefTV);
        this.eeCTV = view.findViewById(R.id.eeCommentTV);
        this.lingNotesTV = view.findViewById(R.id.linguisticNoteTV);
        this.phoneticsTV = view.findViewById(R.id.phoneticsTV);
        this.translationTV = view.findViewById(R.id.translationTV);

        this.phoneticsTV.setVisibility(View.GONE);

        ensureTypeface(view.getContext());

        //TODO delay setting filenames to audioplayer
        // TODO implement playing long quote after short quote
        //  by setting both in media player reset

        //TODO consider implementing for when series of quote files
        // (i.e. screen with word list instead of quote
        //ArrayList<String> audioFilePathsNames
        //        = Globals.getAudioFilePathNames(assetManager, screen);

        this.greekShortTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playShortQuote();
            }
        });

        this.greekLongTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playLongQuote();
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

    private void ensureTypeface(Context context) {
        Typeface prefTypeface = Globals.getPreferredTypeface(
                context);
        this.greekShortTV.setTypeface(prefTypeface);
        this.greekLongTV.setTypeface(prefTypeface);
        this.lingNotesTV.setTypeface(prefTypeface);
        this.titleTV.setTypeface(prefTypeface);
        this.translationTV.setTypeface(prefTypeface);
    }

    @Override
    public void onStart() {
        super.onStart();

        playShortAndLongQuote();

        //TODO FIXME
        // should the media player be shared between fragmants?
        // or should we create and destroy one on each fragment?
        // then we should handle it on fragment lifecycle (close of mp)
    }

    //TODO this is to be called after swipe of new fragment
    // the other individual play methods when user clicks on quote
    void playShortAndLongQuote() {

        String[] paths = new String[] {
                this.shortQuoteAudioAssetPath,
                this.longQuoteAudioAssetPath};

        playQuotes(paths,this.audioPlayer);
    }

    static void playQuotes(String[] quotesAssetsPaths, AudioPlayerHelper player) {

        String[] validAssetPaths = filterNonNullElements(quotesAssetsPaths);

        //TODO FIXME check that we are playing both quotes
        // since this code was used in previous quote activity

        //FIXME audio don't seem to play in API 19 while running tests, plays on 29

        //TODO check that audioplayer is not null

        //TODO show toast for non existing audio files
        // possibility: keep arraylists: one for existing audio files
        // one for quotes that have none
        // one for quotes that should have one but is missing

        //FIXME: Play request non accepted state, ignoring. State: STOPPED
        // after first playback

        try {
            player.reset(validAssetPaths);
            player.play();
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        }
    }

    static String[] filterNonNullElements(String[] arrayList) {
        ArrayList<String> nonNullElementsArrayList = new ArrayList<>();
        for(String element:arrayList) {
            if(element != null) {
                nonNullElementsArrayList.add(element);
            }
        }

        return nonNullElementsArrayList.toArray(new String[]{});
    }

    static void playQuote(String quoteAssetPath, AudioPlayerHelper player) {

        if(Utils.isNullOrEmpty(quoteAssetPath)) {
            Log.e(TAG,"No quote, can't play");
            return;
        }

        playQuotes(new String[]{quoteAssetPath}, player);
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

        //test, FIXME
        this.titleTV.setText(screen.getTitle());
        this.titleTV.setText("position: " + this.position
                + " of " + this.screensCount);

        //TODO
        // populate UI widgets with data for current schermata
        // load screen data into the TV, etc
        // set also the audio player
        // log error message when audio file not found
        setGreekTV(this.greekShortTV, screen.getShortQuote());
        setGreekTV(this.greekLongTV, screen.getFullQuote());

        adjustForEmptyLongQuote();

        //FIXME db gets not refreshed after changes and new run

        //TODO FIXME this.phoneticsTV.setText(screen.);
        //this.phoneticsTV = findViewById(R.id.phoneticsTV);

        this.citationTV.setText(screen.getCitation());
        this.translationTV.setText(screen.getTranslation());
        this.eeCTV.setText(screen.getEasterEggComment());
        this.lingNotesTV.setText(screen.getLinguisticNotes());
    }

    private void adjustForEmptyLongQuote() {

        if(Utils.isNullOrEmpty(screen.getFullQuote())) {

            this.greekLongTV.setVisibility(View.GONE);

            int newPaddingTop = 100 + this.greekShortTV.getPaddingTop();
            int paddingBottom = this.greekShortTV.getPaddingBottom();
            int paddingLeft = this.greekShortTV.getPaddingLeft();
            int paddingRight = this.greekShortTV.getPaddingRight();
            this.greekShortTV.setPadding(paddingLeft,newPaddingTop,
                    paddingRight,paddingBottom);
        }
    }

    private static void setGreekTV(TextView tv, Quote quote) {
        final MyHtmlTagHandler htmlTagHandler
                = new MyHtmlTagHandler();

        //TODO (not in this method)
        // update previous quotes to show in new short/long quote format

        if(quote == null) {
            Log.e(TAG,"Null quote passed.");
            tv.setText("");
        }
        else {
            String quoteTxt = quote.getQuoteText();
            tv.setText(Html.fromHtml(quoteTxt,
                    null,
                    htmlTagHandler));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(SCREEN_ID_BUNDLE_FIELD, this.screenId);
    }
}
