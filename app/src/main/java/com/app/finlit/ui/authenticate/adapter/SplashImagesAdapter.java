package com.app.finlit.ui.authenticate.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.finlit.data.models.TourModel;
import com.app.finlit.ui.authenticate.SplashImagesFragment;

import java.util.List;

public class SplashImagesAdapter extends FragmentPagerAdapter {

    private List<TourModel> screens;

    public SplashImagesAdapter(FragmentManager fragmentManager, List<TourModel> screens) {
        super(fragmentManager);
        this.screens = screens;
    }

    @Override
    public Fragment getItem(int position) {

        return SplashImagesFragment.newInstance(screens.get(position));
    }

    @Override
    public int getCount() {
        return screens.size();
    }
}

