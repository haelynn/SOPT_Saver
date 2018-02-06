package com.sopt.saver.saver.Mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sopt.saver.saver.R;

import java.util.ArrayList;

/**
 * Created by lynn on 2017-06-30.
 */

public class MyPageListViewAdapter extends BaseAdapter{
    private static final int ITEM_VIEW_TYPE_SECTION = 0;
    private static final int ITEM_VIEW_TYPE_STRS = 1;
    private static final int ITEM_VIEW_TYPE_NEXT = 2;
    private static final int ITEM_VIEW_TYPE_TOGG = 3;
    private static final int ITEM_VIEW_TYPE_MAX = 4;

    private Context context;

    private ArrayList<MyPageListViewItem> listViewItemList = new ArrayList<MyPageListViewItem>();

    public MyPageListViewAdapter(){

    }
    public void setContext(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    @Override
    public int getItemViewType(int position) {
        return listViewItemList.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final Context context = parent.getContext();
        int viewType = getItemViewType(position);


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            MyPageListViewItem mItem = listViewItemList.get(position);

            switch(viewType){
                case ITEM_VIEW_TYPE_SECTION:
                    convertView = inflater.inflate(R.layout.mypage_list_section, parent, false);

                    TextView sectionTextView = (TextView)convertView.findViewById(R.id.mypage_section);
                    sectionTextView.setText(mItem.getSection());

                    break;

                case ITEM_VIEW_TYPE_STRS:
                    convertView = inflater.inflate(R.layout.mypage_list_item, parent, false);

                    TextView itemTextView = (TextView)convertView.findViewById(R.id.mypage_item);
                    itemTextView.setText(mItem.getItem());

                    break;

                case ITEM_VIEW_TYPE_NEXT:
                    convertView = inflater.inflate(R.layout.mypage_list_next, parent, false);

                    TextView titleTextView = (TextView)convertView.findViewById(R.id.mypage_title);
                    titleTextView.setText(mItem.getTitle());

                    break;

                case ITEM_VIEW_TYPE_TOGG :
                    convertView = inflater. inflate(R.layout.mypage_list_toggle, parent, false);

                    TextView toggleTextView = (TextView)convertView.findViewById(R.id.mypage_tittle_t);
                    toggleTextView.setText(mItem.getTitle_t());

                    break;
            }


        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    public void addItem(String str, int temp){
        MyPageListViewItem item = new MyPageListViewItem();

        if(temp == 0){
            item.setType(ITEM_VIEW_TYPE_SECTION);
            item.setSection(str);
        }

        else if(temp == 1){
            item.setType(ITEM_VIEW_TYPE_STRS);
            item.setItem(str);
        }

        else if(temp == 2){
            item.setType(ITEM_VIEW_TYPE_TOGG);
            item.setTitle_toggle(str);
        }

        else{
            item.setType(ITEM_VIEW_TYPE_NEXT);
            item.setTitle(str);
        }

        listViewItemList.add(item);
    }
}
