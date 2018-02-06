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
 * Created by kyi42 on 2017-04-15.
 */

public class ERecyclerAdapter extends RecyclerView.Adapter<ItemDataViewHolder> {

    private Context context;
    ArrayList<EItemData> electronics_itemDatas;
    View.OnClickListener clickListener;

    public ERecyclerAdapter(ArrayList<EItemData> electronics_itemDatas, View.OnClickListener clickListener) {
        this.electronics_itemDatas = electronics_itemDatas;
        this.clickListener = clickListener;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void setAdapter(ArrayList<EItemData> electronics_itemDatas) {
        this.electronics_itemDatas = electronics_itemDatas;
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
        if (electronics_itemDatas.get(position).image != null) {
            Glide.with(context)
                    .load(electronics_itemDatas.get(position).image)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        }
        else {
            Glide.with(context)
                    .load(R.drawable.category_electronics2)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        }
        holder.electronics_title_tv.setText(electronics_itemDatas.get(position).title);
        holder.electronics_kind_tv.setText(electronics_itemDatas.get(position).kind);
        holder.electronics_product_tv.setText(electronics_itemDatas.get(position).product);
        holder.electronics_price_tv.setText(electronics_itemDatas.get(position).price);
        holder.electronics_count_tv.setText(electronics_itemDatas.get(position).count);
        holder.electronics_time_tv.setText(electronics_itemDatas.get(position).time);
        holder.electronics_period_tv.setText(electronics_itemDatas.get(position).period);
        holder.electronics_user_id_tv.setText(electronics_itemDatas.get(position).id);
    }

    @Override
    public int getItemCount() {
        return electronics_itemDatas != null ? electronics_itemDatas.size() : 0;
    }
}
