package com.sopt.saver.saver.Message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sopt.saver.saver.R;

public class MessageDetailViewHolder extends RecyclerView.ViewHolder {

    public TextView userName;
    public TextView sendContent;
    public TextView sendDate;
    public TextView recieveContent;
    public TextView recieveDate;


        public MessageDetailViewHolder(View itemView) {
            super(itemView);

            userName = (TextView)itemView.findViewById(R.id.message_id);
//            sendContent= (TextView)itemView.findViewById(R.id.message_send_content);
//            sendDate = (TextView)itemView.findViewById(R.id.message_send_date);
//            recieveContent= (TextView)itemView.findViewById(R.id.message_receive_content);
//            recieveDate= (TextView)itemView.findViewById(R.id.message_receive_date);

        }

}
