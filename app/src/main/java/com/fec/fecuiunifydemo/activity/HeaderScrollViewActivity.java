package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.fec.fecuiunifydemo.R;
import com.fec.fecuiunifydemo.adapter.RefreshAdapter;
import com.fec.view.common.zoom.HeaderScrollView;
import java.util.Arrays;
import java.util.List;

public class HeaderScrollViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView ;
    private HeaderScrollView mScrollView ;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_scroll_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mScrollView = findViewById(R.id.header_scroll_view);
        mScrollView.smoothScrollBy(0, 20);
        mScrollView.setFocusable(true);
        init();
    }

    private void init() {
        mDatas= Arrays.asList("第1条数据","第2条数据","第3条数据","第4条数据","第5条数据","第6条数据","第7条数据","第8条数据","第9条数据","第10条数据");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RefreshAdapter mAdapter = new RefreshAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }
}
