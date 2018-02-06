package com.sopt.saver.saver.Message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hyowon on 2017. 4. 15..
 */

public class MDetailRecyclerAdapter extends RecyclerView.Adapter<MessageDetailViewHolder> {

    private ArrayList<MessageDetailListData> messageDetailListDatas;
    private View.OnClickListener onClick;

    public MDetailRecyclerAdapter(ArrayList<MessageDetailListData> messageDetailListDatas, View.OnClickListener onClickListener) {
        this.messageDetailListDatas = messageDetailListDatas;

        this.onClick = onClickListener;
    }

    public void setAdapter(ArrayList<MessageDetailListData> messageDetailListDatas){
        this.messageDetailListDatas = messageDetailListDatas;
        notifyDataSetChanged();
    }

    @Override
    public MessageDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        View viewSend = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_detail_send, parent,false);
////        View viewRecieve = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_detail_recieve, parent,false);
////        MessageDetailViewHolder viewHolderSend = new MessageDetailViewHolder(viewSend);
////        MessageDetailViewHolder viewHolderRecieve = new MessageDetailViewHolder(viewSend);
////
////        viewSend.setOnClickListener(onClick);
        return null;
    }

    @Override
    public void onBindViewHolder(MessageDetailViewHolder holder, int position) {

        holder.userName.setText(messageDetailListDatas.get(position).userName);
        holder.sendContent.setText(messageDetailListDatas.get(position).sendContent);
        holder.sendDate.setText(messageDetailListDatas.get(position).sendDate);
        holder.recieveContent.setText(messageDetailListDatas.get(position).recieveContent);
        holder.recieveDate.setText(messageDetailListDatas.get(position).recieveDate);
    }

    @Override
    public int getItemCount() {
        return messageDetailListDatas !=null ? messageDetailListDatas.size() : 0;
    }
}
