package com.sopt.saver.saver.Electronics;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kyi42 on 2017-06-28.
 */

public class EProductInfoPagerAdapter extends FragmentStatePagerAdapter {
    int tabcount;
    EProductInfoFragment info_frag = new EProductInfoFragment();
    EProductPictureFragment pict_frag = new EProductPictureFragment();

    public EProductInfoPagerAdapter(FragmentManager fm) {
        super(fm);
        this.tabcount = 2;
    }
    public void setData(EProductInfoFragment info_frag, EProductPictureFragment pict_frag)
    {
        this.info_frag = info_frag;
        this.pict_frag = pict_frag;
    }
    @Override
    public Fragment getItem(int position) {
            switch (position) {
            case 0: {
                return info_frag;
            }
            case 1: {
                return pict_frag;
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
