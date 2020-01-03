package it.collideorscopeapps.codename_hippopotamos;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.AudioPlayerHelper;
import it.collideorscopeapps.codename_hippopotamos.database.QuotesProvider;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;
import it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager.QuoteFragment;
import it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager.QuoteViewModel;

public class QuotePagerActivity extends FragmentActivity {

    //TODO FIXME there is too much delaty before opening the demo/playlist activity

    public static final String TAG = "QuotePagerActivity";
    public static final String DEMO_ACTION = "it.collideorscopeapps.codename_hippopotamos.DEMO";
    public static final String PLAY_ACTION = "it.collideorscopeapps.codename_hippopotamos.PLAY";

    public static final String DEMO_PLAYLIST_NAME = "Recorded quotes";

    private QuoteViewModel mViewModel;
    private TreeMap<Integer, QuoteFragment> quoteFragmentByPosition;
    private TreeMap<Integer, Boolean> positionWasSelectedAtLeastOnce;

    public AudioPlayerHelper getAudioPlayer() {
        return audioPlayer;
    }

    private AudioPlayerHelper audioPlayer;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;
    private ViewPager2.OnPageChangeCallback viewPager2PageChangeCallback;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slide_pager_activity);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        this.quoteFragmentByPosition = new TreeMap<>();
        this.positionWasSelectedAtLeastOnce = new TreeMap<>();

        //TODO here in choosing the adapter, make it according to the intent action
        // i.e. demo or startplaying
        String action = this.getIntent().getAction();
        Log.d(TAG,"Action: " + action);

        QuotesProvider.Languages languageSetting = QuotesProvider.DEFAULT_LANGUAGE;

        if(action == DEMO_ACTION) {
            getData(languageSetting, DEMO_PLAYLIST_NAME);
        } else if(action == PLAY_ACTION) {
            getData(languageSetting);
        } else {
            Log.e(TAG,"No action specified");
        }

        try {
            this.audioPlayer = new AudioPlayerHelper(
                    this.getAssets());
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        }

        pagerAdapter = new QuotePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        viewPager2PageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                boolean pageWasSelectedAtLeastOnceBefore = false;

                if(!positionWasSelectedAtLeastOnce.containsKey(position)) {
                    positionWasSelectedAtLeastOnce.put(position, true);
                } else {
                    pageWasSelectedAtLeastOnceBefore = true;
                }

                quoteFragmentByPosition.get(position).onSelected(
                        pageWasSelectedAtLeastOnceBefore);


            }
        };
        viewPager.registerOnPageChangeCallback(viewPager2PageChangeCallback);
    }

    public void putQuoteFragment(int position,
                                 QuoteFragment quoteFragment) {
        this.quoteFragmentByPosition.put(position, quoteFragment);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //TODO implement close/reopen audio player when activity destroyed,
        // closed and reStarted
        //audio player should be created by activity and passed in fragment
        // constructor
        //onStart, resume, pause, stop, destroy (close?)
        // implement player pause/stop when swiping fragments?
        this.audioPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            this.audioPlayer.close();
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        }

        viewPager.unregisterOnPageChangeCallback(
                viewPager2PageChangeCallback);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public int getScreenCount() {
        return this.mViewModel.getScreenCount();
    }

    private void getData(QuotesProvider.Languages language) {
        getData(language, null);
    }

    private void getData(QuotesProvider.Languages language,
                         String playlistDescriptor) {
        ViewModelProvider viewModelProvider = ViewModelProviders.of(this);
        this.mViewModel
                = viewModelProvider.get(QuoteViewModel.class);

        this.mViewModel.init(language, playlistDescriptor);
    }

    private class QuotePagerAdapter extends FragmentStateAdapter {

        QuotePagerActivity fragActivity;

        public QuotePagerAdapter(QuotePagerActivity fragActivity) {
            super(fragActivity);

            this.fragActivity = fragActivity;
        }

        @Override
        public Fragment createFragment(int position) {

            //TODO
            // code here for switching quote
            // Return a NEW fragment instance in createFragment(int)
            Log.d(TAG,"Creating quoteFragment at " + position);

            Schermata screen = this.fragActivity.mViewModel.getScreenAt(position);

            Log.d(TAG,"Fragment with screen: " + screen.toString());
            QuoteFragment fragment = QuoteFragment.newInstance(
                    position, screen,
                    this.fragActivity.mViewModel.getScreenCount(),
                    fragActivity.getAudioPlayer(),
                    fragActivity.getAssets());
            Bundle args = new Bundle();
            args.putInt(QuoteFragment.SCREEN_ID_BUNDLE_FIELD, position + 1);
            fragment.setArguments(args);

            this.fragActivity.putQuoteFragment(position, fragment);

            return fragment;
        }

        @Override
        public int getItemCount() {
            return this.fragActivity.getScreenCount();
        }

        //TODO handle event onactivity end to release media player

    }
}
