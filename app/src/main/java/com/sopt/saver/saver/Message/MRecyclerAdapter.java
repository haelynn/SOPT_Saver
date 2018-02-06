package com.sopt.saver.saver.Message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.saver.saver.R;

import java.util.ArrayList;

/**
 * Created by hyowon on 2017. 4. 15..
 */

public class MRecyclerAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private Context context;
    private ArrayList<MessageListData> messageListDatas;
    private View.OnClickListener onClickListener;

    public void setContext(Context context) {
        this.context = context;
    }

    public MRecyclerAdapter(ArrayList<MessageListData> mainListDatas, View.OnClickListener onClickListener) {
        this.messageListDatas = mainListDatas;
        this.onClickListener = onClickListener;
    }

    public void setAdapter(ArrayList<MessageListData> mDatas){
        this.messageListDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_main_description, parent,false);
        view.setOnClickListener(onClickListener);
        MessageViewHolder.setContext(context);
        MessageViewHolder viewHolder = new MessageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.recycler_buyerid.setText(messageListDatas.get(position).buyerid);
        holder.recycler_content.setText(messageListDatas.get(position).content);
        holder.recycler_time.setText(messageListDatas.get(position).time);
    }
    @Override
    public int getItemCount() {
        return messageListDatas != null ? messageListDatas.size() : 0;
    }
}
