package com.sopt.saver.saver.Mydeal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sopt.saver.saver.Electronics.ESellerListViewActivity;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kyi42 on 2017-07-03.
 */

public class MydealListAdapter extends BaseAdapter {
    ArrayList<Mydeal_ProductData> mydeal_productDatas;
    View.OnClickListener onClickListener;
    Context context;
    NetworkService service;
    ///////////데이터 전달//////////
    String id;
    String user_num;
    String thing_num;

    ////////////CONSTRUCTOR///////////////
    public MydealListAdapter() {

    }

    public MydealListAdapter(ArrayList<Mydeal_ProductData> mydeal_productDatas, View.OnClickListener onClickListener) {
        this.mydeal_productDatas = mydeal_productDatas;
        this.onClickListener = onClickListener;
    }

    //////////////METHOD/////////////////
    public void setData(String id, String user_num) {
        this.id = id;
        this.user_num = user_num;
    }

    public void setAdapter(ArrayList<Mydeal_ProductData> mydeal_productDatas) {
        this.mydeal_productDatas = mydeal_productDatas;
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mydeal_productDatas != null ? mydeal_productDatas.size() : 0;
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mydeal_item_description, parent, false);
        }
        ImageView mydeal_img = (ImageView) convertView.findViewById(R.id.mydeal_item_img);
        TextView mydeal_title = (TextView) convertView.findViewById(R.id.mydeal_item_text1);
        TextView mydeal_subtitle = (TextView) convertView.findViewById(R.id.mydeal_item_text2);
        TextView mydeal_product = (TextView) convertView.findViewById(R.id.mydeal_item_text3);
        TextView mydeal_price = (TextView) convertView.findViewById(R.id.mydeal_item_text4);
        TextView mydeal_time = (TextView) convertView.findViewById(R.id.mydeal_item_text5);
        TextView mydeal_text6 = (TextView) convertView.findViewById(R.id.mydeal_item_text6);
        TextView mydeal_text7 = (TextView) convertView.findViewById(R.id.mydeal_item_image1_text);
        ///////////////////폰트설정/////////////////////
        /////////////클릭이벤트설정/////////////////////
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mydeal_productDatas.get(position).category.equals("전자제품")) {
                    Intent intent = new Intent(context, ESellerListViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", id);
                    intent.putExtra("user_num", user_num);
                    intent.putExtra("category", "전자제품");
                    thing_num = String.valueOf(mydeal_productDatas.get(position).num);
                    intent.putExtra("thing_num", thing_num);
                    context.startActivity(intent);
                } else if (mydeal_productDatas.get(position).category.equals("이용권")) {
                    Intent intent = new Intent(context, ESellerListViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", id);
                    intent.putExtra("user_num", user_num);
                    intent.putExtra("category", "티켓 및 이용권");
                    thing_num = String.valueOf(mydeal_productDatas.get(position).num);
                    intent.putExtra("thing_num", thing_num);
                    context.startActivity(intent);
                } else if (mydeal_productDatas.get(position).category.equals("브랜드")) {
                    Intent intent = new Intent(context, ESellerListViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", id);
                    intent.putExtra("user_num", user_num);
                    intent.putExtra("category", "브랜드");
                    thing_num = String.valueOf(mydeal_productDatas.get(position).num);
                    intent.putExtra("thing_num", thing_num);
                    context.startActivity(intent);
                } else if (mydeal_productDatas.get(position).category.equals("스마트")) {
                    Intent intent = new Intent(context, ESellerListViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", id);
                    intent.putExtra("user_num", user_num);
                    intent.putExtra("category", "스마트폰 및 가입상품");
                    thing_num = String.valueOf(mydeal_productDatas.get(position).num);
                    intent.putExtra("thing_num", thing_num);
                    context.startActivity(intent);
                } else if (mydeal_productDatas.get(position).category.equals("기타")) {
                    Intent intent = new Intent(context, ESellerListViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", id);
                    intent.putExtra("user_num", user_num);
                    intent.putExtra("category", "기타");
                    thing_num = String.valueOf(mydeal_productDatas.get(position).num);
                    intent.putExtra("thing_num", thing_num);
                    context.startActivity(intent);
                }
            }
        });
        ////////////////뷰 설정/////////////////
        if (mydeal_productDatas.get(position).image != null) {
            Glide.with(context)
                    .load(mydeal_productDatas.get(position).image)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(mydeal_img);
//            Log.i("Image", mydeal_productDatas.get(position).num + " : " + mydeal_productDatas.get(position).image.toString());
        }
        else {
            if(mydeal_productDatas.get(position).category.equals("전자제품")) {
                Glide.with(context)
                        .load(R.drawable.category_electronics2)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into(mydeal_img);
            }
            else if(mydeal_productDatas.get(position).category.equals("이용권"))
            {
                Glide.with(context)
                        .load(R.drawable.category_ticket2)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into(mydeal_img);
            }
            else if(mydeal_productDatas.get(position).category.equals("브랜드"))
            {
                Glide.with(context)
                        .load(R.drawable.category_brand2)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into(mydeal_img);
            }
            else if(mydeal_productDatas.get(position).category.equals("스마트"))
            {
                Glide.with(context)
                        .load(R.drawable.category_smartphone2)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into(mydeal_img);
            }
            else if(mydeal_productDatas.get(position).category.equals("기타"))
            {
                Glide.with(context)
                        .load(R.drawable.category_guita2)
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into(mydeal_img);
            }
        }
        mydeal_title.setText(mydeal_productDatas.get(position).title);
        mydeal_subtitle.setText("[ " + mydeal_productDatas.get(position).kind.toString() + " ] " + mydeal_productDatas.get(position).product);
        mydeal_product.setText(mydeal_productDatas.get(position).product);
        mydeal_price.setText(mydeal_productDatas.get(position).price);
        mydeal_time.setText(mydeal_productDatas.get(position).time);
        mydeal_text6.setText(String.valueOf(mydeal_productDatas.get(position).id));
        mydeal_text7.setText(mydeal_productDatas.get(position).count);

        return convertView;
    }
}
