package com.sopt.saver.saver.Mydeal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kyi42 on 2017-07-03.
 */

public class MydealPagerAdapter extends FragmentStatePagerAdapter {
    int tabcount;
    MydealWriteFragment mydealWriteFragment = new MydealWriteFragment();
    MydealCommentFragment mydealCommentFragment = new MydealCommentFragment();

    public MydealPagerAdapter(FragmentManager fm, int tabcount){
        super(fm);
        this.tabcount = tabcount;
    }
    public void setData(MydealWriteFragment mydealWriteFragment, MydealCommentFragment mydealCommentFragment)
    {
        this.mydealWriteFragment = mydealWriteFragment;
        this.mydealCommentFragment = mydealCommentFragment;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mydealWriteFragment;
            case 1:
                return mydealCommentFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
