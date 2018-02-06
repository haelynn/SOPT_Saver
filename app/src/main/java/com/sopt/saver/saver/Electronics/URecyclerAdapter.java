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

public class URecyclerAdapter extends RecyclerView.Adapter<ItemDataViewHolder> {

    private Context context;
    ArrayList<UItemData> uDatas;
    View.OnClickListener clickListener;

    public URecyclerAdapter(ArrayList<UItemData> uDatas, View.OnClickListener clickListener) {
        this.uDatas = uDatas;
        this.clickListener = clickListener;
    }

    public void setAdapter(ArrayList<UItemData> uDatas) {
        this.uDatas = uDatas;
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
        if (uDatas.get(position).image != null) {
            Glide.with(context)
                    .load(uDatas.get(position).image)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        }
        else
            Glide.with(context)
                    .load(R.drawable.category_ticket2)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        holder.electronics_title_tv.setText(uDatas.get(position).title);
        holder.electronics_kind_tv.setText(uDatas.get(position).kind);
        holder.electronics_product_tv.setText(uDatas.get(position).product);
        holder.electronics_price_tv.setText(uDatas.get(position).price);
        holder.electronics_count_tv.setText(uDatas.get(position).count);
        holder.electronics_time_tv.setText(uDatas.get(position).time);
        holder.electronics_period_tv.setText(uDatas.get(position).period);
        holder.electronics_user_id_tv.setText(uDatas.get(position).id);
    }

    @Override
    public int getItemCount() {
        return uDatas != null ? uDatas.size() : 0;
    }

}
