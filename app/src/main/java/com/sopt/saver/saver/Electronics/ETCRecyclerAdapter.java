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

public class ETCRecyclerAdapter extends RecyclerView.Adapter<ItemDataViewHolder> {

    private Context context;
    ArrayList<ETCItemData> etcDatas;
    View.OnClickListener clickListener;

    public ETCRecyclerAdapter(ArrayList<ETCItemData> etcDatas, View.OnClickListener clickListener) {
        this.etcDatas = etcDatas;
        this.clickListener = clickListener;
    }

    public void setAdapter(ArrayList<ETCItemData> etcDatas) {
        this.etcDatas = etcDatas;
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
        if (etcDatas.get(position).image != null) {
            Glide.with(context)
                    .load(etcDatas.get(position).image)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        }
        else
            Glide.with(context)
                    .load(R.drawable.category_guita2)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        holder.electronics_title_tv.setText(etcDatas.get(position).title);
        holder.electronics_kind_tv.setText(etcDatas.get(position).kind);
        holder.electronics_product_tv.setText(etcDatas.get(position).product);
        holder.electronics_price_tv.setText(etcDatas.get(position).price);
        holder.electronics_count_tv.setText(etcDatas.get(position).count);
        holder.electronics_time_tv.setText(etcDatas.get(position).time);
        holder.electronics_period_tv.setText(etcDatas.get(position).period);
        holder.electronics_user_id_tv.setText(etcDatas.get(position).id);
    }

    @Override
    public int getItemCount() {
        return etcDatas != null ? etcDatas.size() : 0;
    }
}
