package com.app.finlit.ui.dates.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.finlit.ui.dates.AvailableFragment;
import com.app.finlit.ui.dates.ConfirmFragment;
import com.app.finlit.ui.dates.PendingFragment;
import com.app.finlit.ui.dates.SentRequestFragment;


/**
 * Created by MANISH-PC on 9/20/2018.
 */

public class DatesPagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public DatesPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (mNumOfTabs == 3) {
            switch (position) {
                case 0:
                    return AvailableFragment.newInstance();
                case 1:
                    return PendingFragment.newInstance();
                case 2:
                    return ConfirmFragment.newInstance();
                default:
                    return null;
            }
        }
        else {
            switch (position) {
                case 0:
                    return PendingFragment.newInstance();
                case 1:
                    return SentRequestFragment.newInstance();
                default:
                    return null;
            }
        }


    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
