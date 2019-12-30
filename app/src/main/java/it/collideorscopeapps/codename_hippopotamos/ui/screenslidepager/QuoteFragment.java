package it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import it.collideorscopeapps.codename_hippopotamos.MyHtmlTagHandler;
import it.collideorscopeapps.codename_hippopotamos.R;
import it.collideorscopeapps.codename_hippopotamos.database.AudioPlayerHelper;
import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class QuoteFragment extends Fragment {

    public static final String SCREEN_ID_BUNDLE_FIELD = "screenId";

    private QuoteViewModel mViewModel;

    final static String AUDIO_FILES_SUBFOLDER = "audio/";
    AssetManager assetManager;
    AudioPlayerHelper audioPlayerHelper;

    int position;
    Schermata screen;

    TextView titleTV,
            greekShortTV,
            greekLongTV,
            citationTV,
            phoneticsTV,
            translationTV,
            lingNotesTV,
            eeCTV;

    public QuoteFragment(int position, Schermata screen) {
        this.position = position;
        this.screen = screen;
    }

    public static QuoteFragment newInstance(int position, Schermata screen) {
        return new QuoteFragment(position, screen);
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
        //demoCollectionAdapter = new DemoCollectionAdapter(this);
        //viewPager.setAdapter(demoCollectionAdapter);

        this.greekShortTV = view.findViewById(R.id.greekShortTextTV);
        this.greekLongTV = view.findViewById(R.id.greekLongTextTV);
        this.titleTV = view.findViewById(R.id.titleTV);
        this.citationTV = view.findViewById(R.id.citationRefTV);
        this.eeCTV = view.findViewById(R.id.eeCommentTV);
        this.lingNotesTV = view.findViewById(R.id.linguisticNoteTV);
        this.phoneticsTV = view.findViewById(R.id.phoneticsTV);
        this.translationTV = view.findViewById(R.id.translationTV);

        this.phoneticsTV.setVisibility(View.GONE);

        this.assetManager = view.getContext().getAssets();
        ArrayList<String> audioFilePathsNames
                = getAudioFilePathNames(assetManager, screen);

        try {
            this.audioPlayerHelper = new AudioPlayerHelper(
                    assetManager, audioFilePathsNames);
        } catch (IOException e) {
            Log.e("QuoteFragment",e.toString());
        }

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

    static ArrayList<String> getAudioFilePathNames(
            AssetManager assetManager, Schermata screen) {

        ArrayList<String> audioFilePathsNames = new ArrayList<>();

        //TODO FIXME handling of missing quote files
        if(assetExists(assetManager,getShortQuoteAudioFilePath(screen))) {
            audioFilePathsNames.add(getShortQuoteAudioFilePath(screen));
        }

        if(assetExists(assetManager,getLongQuoteAudioFilePath(screen))) {
            audioFilePathsNames.add(getLongQuoteAudioFilePath(screen));
        }

        return audioFilePathsNames;
    }

    static String getShortQuoteAudioFilePath(Schermata screen) {

        return getQuoteAudioFilePath(screen.getShortQuote());
    }

    static String getLongQuoteAudioFilePath(Schermata screen) {

        return getQuoteAudioFilePath(screen.getFullQuote());
    }

    static String getQuoteAudioFilePath(Quote quote) {
        final String EMPTY_STRING = "";

        if(quote == null) {
            return EMPTY_STRING;
        }

        String shortQuoteAudioFilePath = AUDIO_FILES_SUBFOLDER
                + quote.getAudioFileName();

        return shortQuoteAudioFilePath;
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

        //TODO FIXME check that we are playing both quotes
        // since this code was used in previous quote activity

        //TODO check that audioplayer is not null
        this.audioPlayerHelper.play();

        //TODO show toast for non existing audio files
        // possibility: keep arraylists: one for existing audio files
        // one for quotes that have none
        // one for quotes that should have one but is missing
    }

    void playShortQuote() {

    }

    void playLongQuote() {

    }

    //TODO move in some utils
    static boolean assetExists(AssetManager assetManager, String path) {

        boolean exists = false;
        try{
            exists = Arrays.asList(assetManager.list("")).contains(path);
        }
        catch (IOException e) {
            Log.e("QuoteFragment","Getting assets list: " + e.toString());
        }
        finally {
            return exists;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider viewModelProvider = ViewModelProviders.of(this);
        mViewModel = viewModelProvider.get(QuoteViewModel.class);
        // TODO: Use the ViewModel

        //TODO check if we actually need to use Bundle savedInstanceState in some way

        // if (savedInstanceState != null) { ..}
        // Restore last state for current screen
        //this.screenId = savedInstanceState.getInt(SCREEN_ID_BUNDLE_FIELD);
        // else Log.e("QuoteFragment","Null saved state");

        loadWidgets(this.screen);

        //TODO, get screen data from ViewModel
        //Schermata screen = mViewModel.getScreen(this.screenId);

        //TODO slidePagerActivity must someone put the page position
        // (corrensponding activity id), in the Bundle savedInstanceState

    }

    private void loadWidgets(Schermata screen) {

        //TODO set defaults for "(this word is untranslatable)"
        // set a screen where is displayed
        // with doric, epic, ionic and attic
        // some preview/tutorial screen? ..

        //test, FIXME
        this.titleTV.setText(screen.getTitle());
        this.titleTV.setText("position: " + this.position
                + " of " + mViewModel.getScreenCount());

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
            Log.e("QuoteFragment","Null quote passed.");
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
