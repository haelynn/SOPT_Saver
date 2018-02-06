package com.sopt.saver.saver.Electronics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.saver.saver.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kyi42 on 2017-07-03.
 */

public class BRecyclerAdapter extends RecyclerView.Adapter<ItemDataViewHolder>{
    private Context context;
    ArrayList<BItemData> bDatas;
    View.OnClickListener clickListener;

    public BRecyclerAdapter(ArrayList<BItemData> bDatas, View.OnClickListener clickListener) {
        this.bDatas = bDatas;
        this.clickListener = clickListener;
    }

    public void setAdapter(ArrayList<BItemData> bDatas) {
        this.bDatas = bDatas;
        notifyDataSetChanged();
    }

    @Override
    public ItemDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.electronics_item_description, parent, false);
        view.setOnClickListener(clickListener);
        ItemDataViewHolder.setContext(context);
        ItemDataViewHolder ItemDataViewHolder = new ItemDataViewHolder(view);
        return ItemDataViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemDataViewHolder holder, int position) {
        if (bDatas.get(position).image != null) {
            Glide.with(context)
                    .load(bDatas.get(position).image)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        }
        else
            Glide.with(context)
                    .load(R.drawable.category_brand2)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        holder.electronics_title_tv.setText(bDatas.get(position).title);
        holder.electronics_kind_tv.setText(bDatas.get(position).kind);
        holder.electronics_product_tv.setText(bDatas.get(position).product);
        holder.electronics_price_tv.setText(bDatas.get(position).price);
        holder.electronics_count_tv.setText(bDatas.get(position).count);
        holder.electronics_time_tv.setText(bDatas.get(position).time);
        holder.electronics_period_tv.setText(bDatas.get(position).period);
        holder.electronics_user_id_tv.setText(bDatas.get(position).id);
    }

    @Override
    public int getItemCount() {
        return bDatas != null ? bDatas.size() : 0;
    }
}