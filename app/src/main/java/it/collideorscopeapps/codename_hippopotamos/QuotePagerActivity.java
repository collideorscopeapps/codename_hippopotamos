package it.collideorscopeapps.codename_hippopotamos;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import it.collideorscopeapps.codename_hippopotamos.model.Schermata;
import it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager.QuoteFragment;
import it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager.QuoteViewModel;

public class QuotePagerActivity extends FragmentActivity {


    public static final String TAG = "QuotePagerActivity";
    public static final String DEMO_ACTION = "it.collideorscopeapps.codename_hippopotamos.DEMO";
    public static final String PLAY_ACTION = "it.collideorscopeapps.codename_hippopotamos.PLAY";

    private QuoteViewModel mViewModel;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slide_pager_activity);

        getData();

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);

        //TODO here in choosing the adapter, make it according to the intent action
        // i.e. demo or startplaying
        String action = this.getIntent().getAction();
        Log.d(TAG,"Action: " + action);

        if(action == DEMO_ACTION) {
            // load demo playlist
        } else if(action == PLAY_ACTION) {
            // load all playlists
        } else {
            Log.e(TAG,"No action specified");
        }

        pagerAdapter = new QuotePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
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

    private void getData() {
        ViewModelProvider viewModelProvider = ViewModelProviders.of(this);
        this.mViewModel
                = viewModelProvider.get(QuoteViewModel.class);
    }

    /**
     * A simple pager adapter that represents 5 QuoteFragment objects, in
     * sequence.
     */
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
            Log.d("QuotePagerAdapter","Creating quoteFragment at " + position);

            //TODO this should depend on the action of the intent: demo or start playing
            Schermata screen = this.fragActivity.mViewModel.getScreenAt(position);
            Log.d("QuotePagerActivity","Fragment with screen: " + screen.toString());
            Fragment fragment = QuoteFragment.newInstance(position, screen);
            Bundle args = new Bundle();
            args.putInt(QuoteFragment.SCREEN_ID_BUNDLE_FIELD, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return this.fragActivity.getScreenCount();
        }

        //TODO handle event onactivity end to release media player

    }
}
