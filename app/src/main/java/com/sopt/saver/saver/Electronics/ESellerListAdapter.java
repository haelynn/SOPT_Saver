package com.sopt.saver.saver.Electronics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sopt.saver.saver.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kyi42 on 2017-07-01.
 */

public class ESellerListAdapter extends BaseAdapter {
    String id;
    ArrayList<ESellerData> sellerDatas;
    View.OnClickListener onClickListener;
    Context context;
    public ESellerListAdapter(ArrayList<ESellerData> sellerDatas, View.OnClickListener onClickListener){
        this.sellerDatas = sellerDatas;
        this.onClickListener = onClickListener;
    }

    public void setAdapter(ArrayList<ESellerData> sellerDatas) {
        this.sellerDatas = sellerDatas;
        notifyDataSetChanged();
    }

    public void setUserId(String id)
    {
        this.id = id;
    }

    @Override
    public int getCount() {
        return sellerDatas != null ? sellerDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.electronics_item_seller_description, parent, false);
        }
        ImageView seller_img = (ImageView)convertView.findViewById(R.id.electronics_seller_item_img);
        TextView seller_userid = (TextView)convertView.findViewById(R.id.electronics_seller_user_id_text);
        TextView seller_title = (TextView)convertView.findViewById(R.id.electronics_seller_item_text1);
        TextView seller_price = (TextView)convertView.findViewById(R.id.electronics_seller_item_text2);
        TextView seller_method = (TextView)convertView.findViewById(R.id.electronics_seller_item_text3);
        TextView seller_local = (TextView)convertView.findViewById(R.id.electronics_seller_item_text4);
        TextView seller_period = (TextView)convertView.findViewById(R.id.electronics_seller_item_text5);
        Button seller_open_btn = (Button)convertView.findViewById(R.id.electronics_seller_open_btn);
        ////////////////뷰 설정/////////////////
        if(sellerDatas.get(position).profileimage != null) {
            Glide.with(context)
                    .load(sellerDatas.get(position).profileimage)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(seller_img);
        }
        seller_userid.setText(sellerDatas.get(position).sellerid);
        seller_title.setText(sellerDatas.get(position).title);
        seller_price.setText(sellerDatas.get(position).price);
        seller_method.setText(sellerDatas.get(position).method);
        seller_local.setText(sellerDatas.get(position).local);
        seller_period.setText(sellerDatas.get(position).period);
        if(id.equals(seller_userid.getText()))
        {
            //열람하기 수정하기로 변경
            seller_open_btn.setBackgroundResource(R.drawable.detailpage_opentext2);
        }
        seller_open_btn.setOnClickListener(onClickListener);
        ///////////////////폰트 설정///////////////////////////
        return convertView;
    }
}
