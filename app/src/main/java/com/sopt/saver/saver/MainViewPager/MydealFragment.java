package com.sopt.saver.saver.MainViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.Mydeal.MydealCommentFragment;
import com.sopt.saver.saver.Mydeal.MydealListAdapter;
import com.sopt.saver.saver.Mydeal.MydealPagerAdapter;
import com.sopt.saver.saver.Mydeal.MydealWriteFragment;
import com.sopt.saver.saver.Mydeal.Mydeal_ProductData;
import com.sopt.saver.saver.Mydeal.Mydeal_ProductResult;
import com.sopt.saver.saver.Mypage.MyPageActivity;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kyi42 on 2017-07-05.
 */

public class MydealFragment extends Fragment{
    /////////////메모리 객체//////////////////
    ImageView mypage_info_img;
    ImageView find_img;
    ImageView home_img;
    ImageView category_img;
    ImageView message_img;
    ImageView mymy_img;
    EditText find_et;
    TextView upper_tv;
    /////////////뷰페이저 탭레이아웃///////////////
    ViewPager viewPager;
    MydealPagerAdapter mydealPagerAdapter;
    TabLayout tabLayout;

    MydealWriteFragment mydealWriteFragment;
    MydealCommentFragment mydealCommentFragment;
    MydealListAdapter mydealWriteListAdapter;
    MydealListAdapter mydealCommentListAdapter;
    ListView write_lv;
    ListView comment_lv;
    /////////////////네트워크 및 데이터 전달///////////////
    private NetworkService service;
    private FragmentManager fragmentManager;
    String id;
    String user_num;
    public MydealFragment()
    {
        super();
    }
    /////////메소드//////////////////////
    public void setUserData(String id, String user_num)
    {
        this.id = id;
        this.user_num = user_num;
    }
    public void setFragmentManager(FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_mydeal, container, false);
        ////////////////////네트워킹/////////////////////////
        service = ApplicationController.getInstance().getNetworkService();
        ///////////////////////객체 초기화///////////////////////////
        viewPager = (ViewPager) view.findViewById(R.id.mydeal_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.mydeal_tab_layout);
        mypage_info_img = (ImageView) view.findViewById(R.id.mydeal_mypage_img);
        find_img = (ImageView) view.findViewById(R.id.mydeal_find_img);
        find_img.setVisibility(View.GONE);
        find_et = (EditText) view.findViewById(R.id.mydeal_find_et);
        upper_tv = (TextView) view.findViewById(R.id.mydeal_upper_tv);
        //////////////////폰트적용///////////////////////////

        /////////////////////인텐트 및 데이터 관리//////////////////////
        mydealWriteFragment = new MydealWriteFragment();
        mydealCommentFragment = new MydealCommentFragment();
        ////////////////////TEST DISPLAY/////////////////////
        write_lv = new ListView(getActivity());
        comment_lv = new ListView(getActivity());
        ArrayList<Mydeal_ProductData> mydeal_productDatas = new ArrayList<Mydeal_ProductData>();
        mydealPagerAdapter = new MydealPagerAdapter(fragmentManager, 2);

        mydealWriteListAdapter = new MydealListAdapter();
        mydealCommentListAdapter = new MydealListAdapter();
        mydealWriteListAdapter.setData(id, user_num);
        mydealCommentListAdapter.setData(id, user_num);

        mydealWriteListAdapter.setAdapter(mydeal_productDatas);

        mydealWriteFragment.setListView(write_lv);
        mydealWriteFragment.setMydealListAdapter(mydealWriteListAdapter);

        mydealCommentFragment.setListView(comment_lv);
        mydealCommentFragment.setMydealListAdapter(mydealCommentListAdapter);

        mydealPagerAdapter.setData(mydealWriteFragment, mydealCommentFragment);
        ///////////////////////이미지 클릭 이벤트 등록////////////////////////////////
        mypage_info_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPageActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("user_num", user_num);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        find_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (find_et.getVisibility() == View.VISIBLE) {
                    String find_text = find_et.getText().toString();
                } else if (find_et.getVisibility() != View.VISIBLE) {
                    find_et.setVisibility(View.VISIBLE);
                    upper_tv.setVisibility(View.GONE);
                }
            }
        });
        //////////////뷰 페이저 리스너 등록////////////////////////////////////////////////
        tabLayout.addTab(tabLayout.newTab().setText("내가 쓴 글"));
        tabLayout.addTab(tabLayout.newTab().setText("내가 답글 단 글"));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        ///////////////////////뷰페이저 설정/////////////////////////////
        viewPager.setAdapter(mydealPagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//       if (savedInstanceState == null) {
//           //처음 CreateView시 정보 전달...역할
//           Bundle bundle = new Bundle();
//           FragmentManager fm = getSupportFragmentManager();
//           FragmentTransaction transaction = fm.beginTransaction();
//           MydealWriteFragment mydealWriteFragment = new MydealWriteFragment();
//
//           transaction.add(R.id.mydeal_viewpager, mydealWriteFragment , null);
//           transaction.addToBackStack(null);
//           transaction.commit();
//       }
        /////////////////////////서버 통신 시작////////////////////////////////
        Networking();
        return  view;
    }

    ////////////////////////클릭 이벤트 정의////////////////////////
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
//            int itemPosition = recyclerView.getChildPosition(v);
//            int tempId = eDatas.get(itemPosition).elec_num;
//            Intent intent = new Intent(getApplicationContext(), ESellerListViewActivity.class);
//            intent.putExtra("category", upperbar_tv.getText().toString());
//            intent.putExtra("thing_num", String.valueOf(tempId));
//            intent.putExtra("id", id);
//            intent.putExtra("user_num",user_num);
//            startActivity(intent);
        }
    };

    public void Networking()
    {
        ////////WRITE/////////////////
        Call<Mydeal_ProductResult> requestWriteInfo = service.getMydealWriterProductResult(user_num);
        requestWriteInfo.enqueue(new Callback<Mydeal_ProductResult>(){
            @Override
            public void onResponse(Call<Mydeal_ProductResult> call, Response<Mydeal_ProductResult> response) {
                if(response.isSuccessful())
                {
                    mydealWriteListAdapter.setAdapter(response.body().result);
                    mydealWriteFragment.setMydealListAdapter(mydealWriteListAdapter);
                }
                else
                {
                    Toast.makeText(getActivity(), "on Fail", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Mydeal_ProductResult> call, Throwable t) {
                Toast.makeText(getActivity(), "on Failure", Toast.LENGTH_SHORT).show();
            }
        });
        ///////////////COMMENT/////////////////
        Call<Mydeal_ProductResult> requestCommentInfo = service.getMydealCommentProductResult(user_num);
        requestCommentInfo.enqueue(new Callback<Mydeal_ProductResult>() {
            @Override
            public void onResponse(Call<Mydeal_ProductResult> call, Response<Mydeal_ProductResult> response) {
                if(response.isSuccessful())
                {
                    mydealCommentListAdapter.setAdapter(response.body().result);
                    mydealCommentFragment.setMydealListAdapter(mydealCommentListAdapter);

                    mydealPagerAdapter.setData(mydealWriteFragment, mydealCommentFragment);
                    viewPager.setAdapter(mydealPagerAdapter);
                    viewPager.setCurrentItem(0);
                }
                else
                {
                    Toast.makeText(getActivity(), "on Comment Fail", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Mydeal_ProductResult> call, Throwable t) {
                Toast.makeText(getActivity(), "on Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Networking();
    }
}
