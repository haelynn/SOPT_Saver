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
 * Created by kyi42 on 2017-07-05.
 */

public class FindRecyclerAdapter extends RecyclerView.Adapter<ItemDataViewHolder>{
    private Context context;
    ArrayList<FindData> findDatas;
    View.OnClickListener clickListener;

    public FindRecyclerAdapter(ArrayList<FindData> findDatas, View.OnClickListener clickListener) {
        this.findDatas = findDatas;
        this.clickListener = clickListener;
    }

    public void setAdapter(ArrayList<FindData> findDatas) {
        this.findDatas = findDatas;
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
        if (findDatas.get(position).image != null) {
            Glide.with(context)
                    .load(findDatas.get(position).image)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        }
        else
            Glide.with(context)
                    .load(R.drawable.background)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.electronics_item_img);
        holder.electronics_title_tv.setText(findDatas.get(position).title);
        holder.electronics_kind_tv.setText(findDatas.get(position).kind);
        holder.electronics_product_tv.setText(findDatas.get(position).product);
        holder.electronics_price_tv.setText(findDatas.get(position).price);
        holder.electronics_count_tv.setText(String.valueOf(findDatas.get(position).count));
        holder.electronics_time_tv.setText(findDatas.get(position).time);
        holder.electronics_period_tv.setText(findDatas.get(position).period);
        holder.electronics_user_id_tv.setText(findDatas.get(position).id);
    }

    @Override
    public int getItemCount() {
        return findDatas  != null ? findDatas.size() : 0;
    }
}
