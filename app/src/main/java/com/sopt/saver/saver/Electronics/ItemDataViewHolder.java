package com.sopt.saver.saver.Electronics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sopt.saver.saver.R;

/**
 * Created by kyi42 on 2017-04-15.
 */

public class ItemDataViewHolder extends RecyclerView.ViewHolder {
    String id;
    String title;
    String kind;
    String product;
    String price;
    String period;
    String time;
    String profileimage;
    String count;
    static Context context;

    ImageView electronics_item_img;
    TextView electronics_title_tv;
    TextView electronics_kind_tv;
    TextView electronics_product_tv;
    TextView electronics_price_tv;
    TextView electronics_count_tv;
    TextView electronics_time_tv;
    TextView electronics_period_tv;
    TextView electronics_user_id_tv;


    public ItemDataViewHolder(View itemView){
        super(itemView);
        electronics_item_img = (ImageView)itemView.findViewById(R.id.electronics_item_img);
        electronics_title_tv = (TextView)itemView.findViewById(R.id.electronics_item_title_tv);
        electronics_kind_tv = (TextView)itemView.findViewById(R.id.electronics_kind_tv);
        electronics_product_tv = (TextView)itemView.findViewById(R.id.electronics_item_product_tv);
        electronics_price_tv = (TextView)itemView.findViewById(R.id.electronics_item_price_tv);
        electronics_count_tv = (TextView)itemView.findViewById(R.id.electronics_item_count_tv);
        electronics_time_tv = (TextView)itemView.findViewById(R.id.electronics_item_time_tv);
        electronics_period_tv = (TextView)itemView.findViewById(R.id.electronics_item_peroid_tv);
        electronics_user_id_tv = (TextView)itemView.findViewById(R.id.electronics_item_userid_tv);
        //////////////////////폰트 적용//////////////////////////////
    }
    public static void setContext(Context activitycontext) {
        context = activitycontext;
    }
}
