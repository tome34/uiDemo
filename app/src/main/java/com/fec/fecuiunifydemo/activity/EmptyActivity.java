package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.empty.StateView;
import com.fec.view.common.topbar.TopBar;

public class EmptyActivity extends AppCompatActivity {
    private StateView mEmptyView;
    private int       mKey;
    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        mEmptyView = findViewById(R.id.empty_view);
        mTopBar = (TopBar)findViewById(R.id.topbar);
        initTopBar();
        mKey = getIntent().getIntExtra("key",-1);
        //正在加载中
        Dismiss();
    }

    private void Dismiss() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mKey == 0){
                    mEmptyView.showEmpty("");
                }else if (mKey == 1){
                    mEmptyView.showError("");
                }
            }
        },1500);
    }


    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("空页面");
    }
}
