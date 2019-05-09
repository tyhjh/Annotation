package com.dhht.annotation;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dhht.annotationlibrary.view.RecyclerViewScrollListener;
import com.example.recyclelibrary.CommonAdapter;
import com.example.recyclelibrary.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends Activity {


    RecyclerView ryclView;

    List<String> mList = new ArrayList<>();
    List<String> mList2 = new ArrayList<>();

    CommonAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actvity_two);

        mAdapter = new CommonAdapter<String>(this, mList, R.layout.list_item_txt) {
            @Override
            public void onBindView(CommonViewHolder viewHolder, String s) {
                viewHolder.setText(R.id.tvTxt, s);
            }
        };

        mockData();
        ryclView = findViewById(R.id.ryclView);
        ryclView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ryclView.setAdapter(mAdapter);
        ryclView.addOnScrollListener(new RecyclerViewScrollListener() {

            @Override
            public void onScrollToBottom() {
                mList.addAll(mList2);
                mAdapter.replaceData(mList);
            }
        });
    }

    private void mockData() {
        mList.add("胸大师傅");
        mList.add("事发地时");
        mList.add("防守打法");
        mList.add("发斯蒂芬");
        mList.add("发的发的");
        mList.add("让他也让他");
        mList.add("可男可女可");
        mList.add("胸大师傅");
        mList.add("事发地时");
        mList.add("防守打法");
        mList.add("发斯蒂芬");
        mList.add("发的发的");
        mList.add("让他也让他");
        mList.add("可男可女可");

        mList2.add("胸大师傅");
        mList2.add("事发地时");
        mList2.add("防守打法");
        mList2.add("发斯蒂芬");
        mList2.add("发的发的");
        mList2.add("让他也让他");
        mList2.add("可男可女可");

    }

}
