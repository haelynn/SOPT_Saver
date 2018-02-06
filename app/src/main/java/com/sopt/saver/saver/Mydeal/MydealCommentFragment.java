package com.sopt.saver.saver.Mydeal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sopt.saver.saver.R;

/**
 * Created by kyi42 on 2017-07-03.
 */

public class MydealCommentFragment extends Fragment {
    ListView listView;
    MydealListAdapter mydealListAdapter;
    public MydealCommentFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mydeal_comment, container, false);
        listView = (ListView)view.findViewById(R.id.frag_mydeal_comment_lv);
        listView.setAdapter(mydealListAdapter);
        return view;
    }
    public void setListView(ListView listView)
    {
        this.listView = listView;
    }
    public void setMydealListAdapter(MydealListAdapter mydealListAdapter)
    {
        this.mydealListAdapter = mydealListAdapter;
    }
}
