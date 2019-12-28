package it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.collideorscopeapps.codename_hippopotamos.R;

public class QuoteFragment extends Fragment {

    public static final String SCREEN_ID_BUNDLE_FIELD = "screenId";

    private QuoteViewModel mViewModel;

    int screenId;
    int position;

    TextView titleTV,
            greekShortTV,
            greekLongTV,
            citationTV,
            phoneticsTV,
            translationTV,
            lingNotesTV,
            eeCTV;

    public QuoteFragment(int position) {
        this.position = position;
    }

    public static QuoteFragment newInstance(int position) {
        return new QuoteFragment(position);
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider viewModelProvider = ViewModelProviders.of(this);
        mViewModel = viewModelProvider.get(QuoteViewModel.class);
        // TODO: Use the ViewModel

        if (savedInstanceState != null) {
            // Restore last state for current screen
            this.screenId = savedInstanceState.getInt(SCREEN_ID_BUNDLE_FIELD);

            //test FIXME
            this.titleTV.setText("position: " + this.screenId
                    + " of " + mViewModel.getScreenCount());

            //TODO, get screen data from ViewModel
            //Schermata screen = mViewModel.getScreen(this.screenId);

            //TODO slidePagerActivity must someone put the page position
            // (corrensponding activity id), in the Bundle savedInstanceState
        }
        else {
            Log.e("QuoteFragment","Null saved state");
            this.titleTV.setText("position: " + this.position
                    + " of " + mViewModel.getScreenCount());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCREEN_ID_BUNDLE_FIELD, this.screenId);
    }

}
