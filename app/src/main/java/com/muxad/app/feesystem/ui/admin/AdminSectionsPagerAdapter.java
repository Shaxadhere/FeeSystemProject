package com.muxad.app.feesystem.ui.admin;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.muxad.app.feesystem.AddAttendanceFragment;
import com.muxad.app.feesystem.AddFeeFragment;
import com.muxad.app.feesystem.AttendanceFragment;
import com.muxad.app.feesystem.FeeDetailsFragment;
import com.muxad.app.feesystem.R;

public class AdminSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.admin_tab_text_1, R.string.admin_tab_text_2};
    private final Context mContext;

    public AdminSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new AddFeeFragment();
                break;
            case 1:
                fragment = new AddAttendanceFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}