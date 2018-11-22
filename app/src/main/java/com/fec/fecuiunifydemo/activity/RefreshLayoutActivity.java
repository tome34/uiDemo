package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fec.fecuiunifydemo.R;
import com.fec.fecuiunifydemo.adapter.RefreshAdapter;
import com.fec.view.common.topbar.TopBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.Arrays;
import java.util.List;

public class RefreshLayoutActivity extends AppCompatActivity {

    private TopBar mTopBar;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<String> mDatas= Arrays.asList("第1条数据","第2数据","第3条数据","第4条数据","第5条数据","第6条数据","第7条数据","第8条数据","第9条数据","第10条数据","第11条数据","第12条数据","第13条数据","第14条数据","第15条数据","第16条数据","第17条数据","第18条数据","第19条数据","第20条数据");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);
        mTopBar = findViewById(R.id.topbar);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        initTopBar();
        //触发自动刷新
        mRefreshLayout.autoRefresh();
        init();
    }

    private void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RefreshAdapter mAdapter = new RefreshAdapter(RefreshLayoutActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("下拉刷新");

    }


}