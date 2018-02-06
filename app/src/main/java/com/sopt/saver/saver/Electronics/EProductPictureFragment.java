package com.sopt.saver.saver.Electronics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sopt.saver.saver.R;

/**
 * Created by kyi42 on 2017-06-28.
 */

public class EProductPictureFragment extends Fragment {
    private ImageView prod_img;
    private TextView comment_tv;
    public EProductPictureFragment() {
        super();
    }
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productpicture, container, false);
        prod_img = (ImageView) view.findViewById(R.id.frag_prod_img);
        comment_tv = (TextView) view.findViewById(R.id.frag_pict_item_image1_text);
        try {
            if (getArguments().get("image") != null) {
                Glide.with(context)
                        .load(getArguments().get("image"))
                        .into(prod_img);
            }
            else
            {
                Glide.with(context)
                        .load(R.drawable.photobox)
                        .into(prod_img);
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        comment_tv.setText(getArguments().get("count").toString());
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCount(String count)
    {
        comment_tv.setText(count);
    }
}
