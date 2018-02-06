package com.sopt.saver.saver.MainViewPager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;

public class MainViewPagerActivity extends AppCompatActivity {
    ///////////////폰트////////////////////////

    //////////////////뷰페이저 관련////////////////////////
    View tabIcon1;
    View tabIcon2;
    View tabIcon3;
    View tabIcon4;

    ViewPager viewPager;
    MainViewPagerAdapter mainViewPagerAdapter;
    TabLayout tabLayout;

    MainPageFragment mainPageFragment;
    CategoryFragment categoryFragment;
    MessageFragment messageFragment;
    MydealFragment mydealFragment;
    /////////////////네트워크 및 데이터 전달///////////////
    private NetworkService service;
    String id;
    String user_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_pager);
        //////////////인텐트 전달//////////////////////
        id = getIntent().getExtras().getString("id");
        user_num = getIntent().getExtras().getString("user_num");
        ///////////////객체 초기화///////////////////////
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.main_view_pager_tab_layout);
        /////////////////프레그먼트 관리////////////////////
        mainPageFragment = new MainPageFragment();
        categoryFragment = new CategoryFragment();
        messageFragment = new MessageFragment();
        mydealFragment = new MydealFragment();

        mainPageFragment.setContext(getApplicationContext());
        mainPageFragment.setAssetManager(getAssets());
        mainPageFragment.setUserData(id, user_num);

        categoryFragment.setContext(getApplicationContext());
        categoryFragment.setAssetManager(getAssets());
        categoryFragment.setUserData(id, user_num);

        messageFragment.setContext(getApplicationContext());
        messageFragment.setAssetManager(getAssets());
        messageFragment.setUserData(id, user_num);

        mydealFragment.setUserData(id, user_num);
        mydealFragment.setFragmentManager(getSupportFragmentManager());
        ///////////////////////탭 아이콘//////////////////////////
        tabIcon1 = getLayoutInflater().inflate(R.layout.customtab, null);
        tabIcon1.findViewById(R.id.icon).setBackgroundResource(R.drawable.tab_icon1_selector);
        tabIcon2 = getLayoutInflater().inflate(R.layout.customtab, null);
        tabIcon2.findViewById(R.id.icon).setBackgroundResource(R.drawable.tab_icon2_selector);
        tabIcon3 = getLayoutInflater().inflate(R.layout.customtab, null);
        tabIcon3.findViewById(R.id.icon).setBackgroundResource(R.drawable.tab_icon3_selector);
        tabIcon4 = getLayoutInflater().inflate(R.layout.customtab, null);
        tabIcon4.findViewById(R.id.icon).setBackgroundResource(R.drawable.tab_icon4_selector);


        /////////////////////////탭 레이아웃 & 뷰페이저/////////////////////////
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabIcon1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabIcon2));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabIcon3));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabIcon4));
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), 4);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        mainViewPagerAdapter.setData(mainPageFragment, mydealFragment, messageFragment, categoryFragment);
        viewPager.setAdapter(mainViewPagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.getTabAt(0).setIcon(R.drawable.main_tapbarhome2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                } else if (tab.getPosition() == 1) {
                } else if (tab.getPosition() == 2) {
                } else if (tab.getPosition() == 3) {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
    public void setCurrentPage(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if(viewPager.getCurrentItem() == 0)
                    moveTaskToBack(true);
                else
                    viewPager.setCurrentItem(0);
                return true;
        }
        return false;
    }
}
