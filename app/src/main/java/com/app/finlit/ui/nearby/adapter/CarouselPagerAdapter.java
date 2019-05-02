package com.app.finlit.ui.nearby.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.ui.nearby.CarouselFragment;

import java.util.List;


public class CarouselPagerAdapter extends FragmentPagerAdapter {

    private List<UserModel> screens;
    private int adapterType;;

    public CarouselPagerAdapter(FragmentManager fragmentManager, List<UserModel> screens, int adapterType) {
        super(fragmentManager);
        this.screens = screens;
    }

    @Override
    public Fragment getItem(int position) {

        return CarouselFragment.newInstance(screens.get(position), adapterType);
    }

    @Override
    public int getCount() {
        return screens.size();
    }
}
