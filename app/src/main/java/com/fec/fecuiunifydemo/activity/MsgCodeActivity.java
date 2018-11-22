package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.ValidateCodeTimer;
import com.fec.view.common.topbar.TopBar;
import io.reactivex.disposables.CompositeDisposable;

public class MsgCodeActivity extends AppCompatActivity {

    private TopBar mTopBar;
    private ValidateCodeTimer mGetCode;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_code);
        mTopBar = (TopBar)findViewById(R.id.topbar);
        mGetCode = findViewById(R.id.get_code);
        initTopBar();
        init();

    }

    private void init() {

        mGetCode.showTimer(MsgCodeActivity.this, mGetCode);
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("验证码倒计时");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGetCode.clearDisposable();

    }
}
