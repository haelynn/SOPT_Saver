package com.sopt.saver.saver.PointPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.R;

import java.util.ArrayList;

/**
 * Created by lynn on 2017-07-01.
 */

public class PointPageListViewAdapter extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_SECTION = 0;
    private static final int ITEM_VIEW_TYPE_ACCOUNT = 1;
    private static final int ITEM_VIEW_TYPE_MONEY = 2;
    private static final int ITEM_VIEW_TYPE_NEXT = 3;
    private static final int ITEM_VIEW_TYPE_MAX = 4;

    private ArrayList<PointPageListViewItem> listViewItemList = new ArrayList<PointPageListViewItem>();

    public PointPageListViewAdapter(){

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

        final Context context = parent.getContext();
        int viewType = getItemViewType(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            PointPageListViewItem listViewItem = listViewItemList.get(position);

            switch(viewType){
                case ITEM_VIEW_TYPE_SECTION:
                    convertView = inflater.inflate(R.layout.pointpage_list_section, parent, false);
                    TextView sectionTextView = (TextView) convertView.findViewById(R.id.pointpage_section);
                    sectionTextView.setText(listViewItem.getSection());
                    break;

                case ITEM_VIEW_TYPE_ACCOUNT:
                    convertView = inflater.inflate(R.layout.pointpage_list_account, parent, false);
                    TextView accountTextView = (TextView)convertView.findViewById(R.id.pointpage_account);
                    TextView accountNumTextView = (TextView)convertView.findViewById(R.id.pointpage_accountnum);
                    TextView txtTextView = (TextView)convertView.findViewById(R.id.point_account_txt);

                    accountTextView.setText(listViewItem.getAccount());
                    accountNumTextView.setText(listViewItem.getAccount_num());
                    break;

                case ITEM_VIEW_TYPE_NEXT:
                    convertView = inflater.inflate(R.layout.pointpage_list_next, parent, false);
                    TextView titleTextView = (TextView)convertView.findViewById(R.id.pointpage_title);
                    titleTextView.setText(listViewItem.getTitle());
                    break;

                case ITEM_VIEW_TYPE_MONEY:
                    convertView = inflater.inflate(R.layout.pointpage_list_money, parent, false);
                    TextView moneyTextView = (TextView)convertView.findViewById(R.id.pointpage_money);
                    ImageButton charge_btn = (ImageButton)convertView.findViewById(R.id.pointpage_money_btn);
                    charge_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "서비스 준비중입니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    EditText money_edit = (EditText)convertView.findViewById(R.id.pointpage_money_edit);
                    moneyTextView.setText(listViewItem.getMoney());

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

    public void addItem(String section){
        PointPageListViewItem item = new PointPageListViewItem();

        item.setType(ITEM_VIEW_TYPE_SECTION);
        item.setSection(section);

        listViewItemList.add(item);
    }

    public void addItem(String account, String accountNum){
        PointPageListViewItem item = new PointPageListViewItem();

        item.setType(ITEM_VIEW_TYPE_ACCOUNT);
        item.setAccount(account);
        item.setAccountNum(accountNum);

        listViewItemList.add(item);
    }

    public void addItem(String str, int temp){
        PointPageListViewItem item = new PointPageListViewItem();

        if(temp == 0){
            item.setType(ITEM_VIEW_TYPE_NEXT);
            item.setTitle(str);
        }

        else if(temp == 1){
            item.setType(ITEM_VIEW_TYPE_MONEY);
            item.setMoney(str);
        }

        listViewItemList.add(item);
    }

}
