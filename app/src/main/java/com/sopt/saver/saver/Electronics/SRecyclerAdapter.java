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

public class SRecyclerAdapter extends RecyclerView.Adapter<ItemDataViewHolder> {

    private Context context;
    ArrayList<SItemData> sDatas;
    View.OnClickListener clickListener;

    public SRecyclerAdapter(ArrayList<SItemData> sDatas, View.OnClickListener clickListener) {
        this.sDatas = sDatas;
        this.clickListener = clickListener;
    }

    public void setAdapter(ArrayList<SItemData> sDatas) {
        this.sDatas = sDatas;
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
        if (sDatas.get(position).image != null) {
            Glide.with(context)
                    .load(sDatas.get(position).image)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        }
        else
            Glide.with(context)
                    .load(R.drawable.category_smartphone2)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        holder.electronics_title_tv.setText(sDatas.get(position).title);
        holder.electronics_kind_tv.setText(sDatas.get(position).kind);
        holder.electronics_product_tv.setText(sDatas.get(position).product);
        holder.electronics_price_tv.setText(sDatas.get(position).price);
        holder.electronics_count_tv.setText(sDatas.get(position).count);
        holder.electronics_time_tv.setText(sDatas.get(position).time);
        holder.electronics_period_tv.setText(sDatas.get(position).period);
        holder.electronics_user_id_tv.setText(sDatas.get(position).id);
    }

    @Override
    public int getItemCount() {
        return sDatas != null ? sDatas.size() : 0;
    }
}
