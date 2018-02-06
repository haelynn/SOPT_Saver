package com.sopt.saver.saver.MainViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sopt.saver.saver.Electronics.ERecyclerListActivity;
import com.sopt.saver.saver.Electronics.ERecyclerSearchActivity;
import com.sopt.saver.saver.Mypage.MyPageActivity;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;

public class CategoryFragment extends Fragment {
    NetworkService service;
    private Intent intent;
    private Context context;
    private AssetManager assetManager;
    String id;
    String user_num;
    //////////////////객체///////////////////////
    private EditText find_et;
    private TextView upper_tv;
    private ImageView mypage_img;
    private ImageView find_img;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    public CategoryFragment()
    {
        super();
    }
    //////////////////메소드//////////////////////
    public void setContext(Context context)
    {
        this.context = context;
    }
    public void setAssetManager(AssetManager assetManager)
    {
        this.assetManager = assetManager;
    }
    public void setUserData(String id, String user_num)
    {
        this.id = id;
        this.user_num = user_num;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_category, container, false);
        /////////////////////객체화//////////////////////////////////
        mypage_img = (ImageView)view.findViewById(R.id.category_mypage_img);
        find_img = (ImageView)view.findViewById(R.id.category_find_img);
        img1 = (ImageView)view.findViewById(R.id.cate_img1);
        img2 = (ImageView)view.findViewById(R.id.cate_img2);
        img3 = (ImageView)view.findViewById(R.id.cate_img3);
        img4 = (ImageView)view.findViewById(R.id.cate_img4);
        img5 = (ImageView)view.findViewById(R.id.cate_img5);
        img6 = (ImageView)view.findViewById(R.id.cate_img6);
        upper_tv = (TextView)view.findViewById(R.id.category_upper_tv);
        find_et = (EditText)view.findViewById(R.id.category_find_et);
        ////////////////폰트적용/////////////////////////

        /////////////////클릭이벤트////////////////
        intent = new Intent(getActivity(), ERecyclerListActivity.class);
        intent.putExtra("user_num", user_num);
        intent.putExtra("id", id);
        mypage_img.setOnClickListener(new View.OnClickListener() {
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
                if(find_et.getVisibility() == View.VISIBLE)
                {
                    Intent find_intent = new Intent(getActivity(), ERecyclerSearchActivity.class);
                    find_intent.putExtra("user_num", user_num);
                    find_intent.putExtra("id", id);
                    find_intent.putExtra("find_text", find_et.getText().toString());
                    startActivity(find_intent);
                }
                else if(find_et.getVisibility() != View.VISIBLE)
                {
                    find_et.setVisibility(View.VISIBLE);
                    upper_tv.setVisibility(View.GONE);
                }
            }
        });

        find_img.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            Intent find_intent = new Intent(getActivity(), ERecyclerSearchActivity.class);
                            find_intent.putExtra("id", id);
                            find_intent.putExtra("user_num", user_num);
                            find_intent.putExtra("find_text", find_et.getText().toString());
                            startActivity(find_intent);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "전자제품");
                startActivity(intent);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "티켓 및 이용권");
                startActivity(intent);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "브랜드");
                startActivity(intent);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "스마트폰 및 가입상품");
                startActivity(intent);
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "기타");
                startActivity(intent);
            }
        });
        return view;
    }
}
