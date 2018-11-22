package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.countDownTime.CountdownView;
import com.fec.view.common.topbar.TopBar;

public class CustomdownTimeActivity extends AppCompatActivity {

    private TopBar mTopBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customdown_time);
        mTopBar = (TopBar)findViewById(R.id.topbar);
        initTopBar();

        CountdownView mCvCountdownViewTest2 = (CountdownView)findViewById(R.id.cv_countdownViewTest2);
        mCvCountdownViewTest2.setTag("test2");
        long time2 = (long)5 * 60 * 60 * 1000;
        mCvCountdownViewTest2.start(time2);

        CountdownView mCvCountdownViewTest3 = (CountdownView)findViewById(R.id.cv_countdownViewTest3);
        long time3 = (long)9 * 60 * 60 * 1000;
        mCvCountdownViewTest3.start(time3);

        CountdownView mCvCountdownViewTest4 = (CountdownView)findViewById(R.id.cv_countdownViewTest4);
        long time4 = (long)150 * 24 * 60 * 60 * 1000;
        mCvCountdownViewTest4.start(time4);

    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("倒计时");
    }
}
