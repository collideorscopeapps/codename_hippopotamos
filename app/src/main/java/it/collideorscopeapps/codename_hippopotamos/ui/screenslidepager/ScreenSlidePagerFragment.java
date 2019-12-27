package it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.collideorscopeapps.codename_hippopotamos.R;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class ScreenSlidePagerFragment extends Fragment {

    final String SCREEN_ID_BUNDLE_FIELD = "screenId";

    private ScreenSlidePagerViewModel mViewModel;

    int screenId;

    public static ScreenSlidePagerFragment newInstance() {
        return new ScreenSlidePagerFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider viewModelProvider = ViewModelProviders.of(this);

        mViewModel = viewModelProvider.get(ScreenSlidePagerViewModel.class);
        // TODO: Use the ViewModel

        if (savedInstanceState != null) {
            // Restore last state for current screen
            this.screenId = savedInstanceState.getInt(SCREEN_ID_BUNDLE_FIELD);

            //TODO, get screen data from ViewModel
            Schermata screen = mViewModel.getScreen(this.screenId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCREEN_ID_BUNDLE_FIELD, this.screenId);
    }

}
