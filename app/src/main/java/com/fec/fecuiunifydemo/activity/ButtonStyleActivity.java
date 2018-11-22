package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.topbar.TopBar;

public class ButtonStyleActivity extends AppCompatActivity {

    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_style);
        mTopBar = (TopBar)findViewById(R.id.topbar);
        initTopBar();
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("按钮样式");
    }
}
