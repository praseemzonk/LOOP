package com.zonk.fbtest.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

import com.zonk.fbtest.Fragment.ConnectFragment;
import com.zonk.fbtest.Fragment.NearbyFragment;
import com.zonk.fbtest.Fragment.ProfileFragment;

public class DashBoardAdapter extends FragmentPagerAdapter {
    LayoutInflater layoutInflater;

    public DashBoardAdapter(FragmentManager fm, Context context) {
        super(fm);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new ConnectFragment();
        }
        if (i == 1) {
            return new  NearbyFragment();
        }

        if (i == 2) {
            return new ProfileFragment();
        }
        else
            return null;
    }

    @Override
    public int getCount() {
        return 3;
    }





}
