package com.sopt.saver.saver.Electronics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kyi42 on 2017-06-28.
 */

public class EProductInfoFragment extends Fragment {
    ImageView product_profile_img;
    TextView product_title_tv;
    TextView product_upper_tv;
    TextView product_time_tv;
    TextView product_writer_user_id_tv;
    TextView product_comment_num_tv;
    TextView product_tv1;
    TextView product_tv2;
    TextView product_tv3;
    TextView product_tv4;
    TextView product_tv5;
    Button product_answer_btn;

    View.OnClickListener onClickListener;

    public EProductInfoFragment() {
        super();
    }

    private Context context;
    String category;
    private NetworkService service;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productinfo, container, false);
        category = getArguments().getString("category");
        service = ApplicationController.getInstance().getNetworkService();
        ///////////////////객체화///////////////////////////
        product_profile_img = (ImageView) view.findViewById(R.id.electronics_prodInfo_item_img);
        product_title_tv = (TextView) view.findViewById(R.id.electronics_prodInfo_title_tv);
        product_writer_user_id_tv = (TextView) view.findViewById(R.id.electronics_prodInfo_userid_tv);
        product_time_tv = (TextView) view.findViewById(R.id.electronics_prodInfo_time_tv);
        product_comment_num_tv = (TextView) view.findViewById(R.id.electronics_prodInfo_item_image1_text);
        product_tv1 = (TextView) view.findViewById(R.id.electronics_prodInfo_item_text1);
        product_tv2 = (TextView) view.findViewById(R.id.electronics_prodInfo_item_text2);
        product_tv3 = (TextView) view.findViewById(R.id.electronics_prodInfo_item_text3);
        product_tv4 = (TextView) view.findViewById(R.id.electronics_prodInfo_item_text4);
        product_tv5 = (TextView) view.findViewById(R.id.electronics_prodInfo_item_text5);
        /////////////////////////폰트 클래스////////////////////////////////

        ////////////////////////////////////////////////////////////////////
        product_answer_btn = (Button) view.findViewById(R.id.electronics_prodInfo_answer_btn);
        if(getArguments().getString("same").equals("1")) {
            ////////////////버튼변경/////////////////////
            product_answer_btn.setBackgroundResource(R.drawable.mydealmywrite_edit);
        }
        product_answer_btn.setOnClickListener(onClickListener);
        if (getArguments() != null) {
            try {
                if(getArguments().get("profileimage") != null)
                    Glide.with(context)
                            .load(getArguments().get("profileimage"))
                            .bitmapTransform(new CropCircleTransformation(context))
                            .into(product_profile_img);
                else
                    Glide.with(context)
                            .load(R.drawable.photobox)
                            .bitmapTransform(new CropCircleTransformation(context))
                            .into(product_profile_img);
            }
            catch (NullPointerException e)
            {

            }
            product_writer_user_id_tv.setText(getArguments().getString("writer_user_id"));
            product_time_tv.setText(getArguments().getString("time"));
            product_comment_num_tv.setText(getArguments().getString("count"));

            product_title_tv.setText(getArguments().getString("title"));
            product_tv1.setText("상품   " + getArguments().getString("product"));
            product_tv2.setText("찾은 가격   " + getArguments().getString("price"));
            product_tv3.setText("기한   " + getArguments().getString("period"));
            product_tv4.setText("요구사항   " + getArguments().getString("explantion"));
            product_tv5.setText("추가정보   " + getArguments().getString("addinformation"));
        }
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setAnsBtnOnClickListener(View.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    public void setCount(String count)
    {
        product_comment_num_tv.setText(count);
    }
}
