package com.sopt.saver.saver.MainViewPager;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sopt.saver.saver.Message.MRecyclerAdapter;
import com.sopt.saver.saver.Message.MessageListData;
import com.sopt.saver.saver.Message.MessageResult;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kyi42 on 2017-07-05.
 */

public class MessageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private NetworkService service;
    private Context context;
    private AssetManager assetManager;
    private String id;
    private String user_num;
    private RecyclerView recyclerView;

    private ArrayList<MessageListData> mDatas;
    private MRecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SwipeRefreshLayout refreshLayout;

    public MessageFragment() {
        super();
    }

    //////////////////////메소드//////////////////////////
    public void setContext(Context context) {
        this.context = context;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void setUserData(String id, String user_num) {
        this.id = id;
        this.user_num = user_num;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_message, container, false);
        ////////////////////////서비스 객체 초기화////////////////////////
        service = ApplicationController.getInstance().getNetworkService();
        ////////////////////////뷰 객체 초기화////////////////////////
        recyclerView = (RecyclerView) view.findViewById(R.id.main_message_recycler);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.main_message_refresh);
        recyclerView.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(this);
        ///////////////////////폰트설정/////////////////////////////

        //////////////////레이아웃 매니저 설정/////////////////////
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.scrollToPosition(0);
        ////////////////데이터 초기화///////////////////
        mDatas = new ArrayList<MessageListData>();
        mDatas.add(new MessageListData());
        mRecyclerAdapter = new MRecyclerAdapter(mDatas, clickEvent);
        recyclerView.setAdapter(mRecyclerAdapter);
        Networking();
        return view;
    }

    @Override
    public void onRefresh() {
        //페이지 리로드 필요
        ListReload();
        refreshLayout.setRefreshing(false);
        Toast.makeText(context, "페이지 리로드", Toast.LENGTH_SHORT).show();
    }

    //    }
    public void ListReload(){
        //네트워킹 리스트 리로드
        Networking();
    }
    public void Networking(){
        Call<MessageResult> getMessage = service.getMessage(id);
        getMessage.enqueue(new Callback<MessageResult>() {
            @Override
            public void onResponse(Call<MessageResult> call, Response<MessageResult> response) {
                if(response.isSuccessful())
                {
                    if(response.body().message.equals("ok"))
                    {
                        mDatas = response.body().result;
                        mRecyclerAdapter.setAdapter(mDatas);
                        recyclerView.setAdapter(mRecyclerAdapter);
//                        Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "NOT OK", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "CONNECTED BUT FAILED", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MessageResult> call, Throwable t) {
                Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            Toast.makeText(getActivity(), "서비스 준비 중", Toast.LENGTH_SHORT).show();
        }
    };
}
