package it.collideorscopeapps.codename_hippopotamos.ui.screenslidepager;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.collideorscopeapps.codename_hippopotamos.R;

public class ScreenSlidePagerFragment extends Fragment {

    private ScreenSlidePagerViewModel mViewModel;

    public static ScreenSlidePagerFragment newInstance() {
        return new ScreenSlidePagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_slide_pager_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScreenSlidePagerViewModel.class);
        // TODO: Use the ViewModel
    }

}
