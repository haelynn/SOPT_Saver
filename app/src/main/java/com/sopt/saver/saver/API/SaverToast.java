package com.sopt.saver.saver.API;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.R;

/**
 * Created by kyi42 on 2017-07-07.
 */

public class SaverToast {
    String string;
    public SaverToast()
    {

    }

    public void setString(String string)
    {
        this.string = string;
    }
    public SaverToast(Context context, LayoutInflater layoutInflater)
    {
        LayoutInflater inflater = layoutInflater;
        LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.saver_toast, null);


        ImageView image = (ImageView) linearLayout.findViewById(R.id.image);
        TextView text = (TextView) linearLayout.findViewById(R.id.text);
        text.setText(string);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(linearLayout);
        toast.show();
    }
}
