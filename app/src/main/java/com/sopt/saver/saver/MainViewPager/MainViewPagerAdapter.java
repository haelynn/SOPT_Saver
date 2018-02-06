package com.sopt.saver.saver.MainViewPager;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kyi42 on 2017-07-05.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter{
    int tabcount;
    MainPageFragment mainPageFragment = new MainPageFragment();
    MydealFragment mydealFragment = new MydealFragment();
    MessageFragment messageFragment = new MessageFragment();
    CategoryFragment categoryFragment = new CategoryFragment();

    public MainViewPagerAdapter(FragmentManager fm, int tabcount) {
        super(fm);
        this.tabcount = tabcount;
    }
    public void setData(MainPageFragment mainPageFragment, MydealFragment mydealFragment, MessageFragment messageFragment, CategoryFragment categoryFragment)
    {
        this.mainPageFragment = mainPageFragment;
        this.mydealFragment = mydealFragment;
        this.messageFragment = messageFragment;
        this.categoryFragment = categoryFragment;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return mainPageFragment;
            }
            case 1:{
                return categoryFragment;
            }
            case 2: {
                return messageFragment;
            }
            case 3: {
                return mydealFragment;
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
