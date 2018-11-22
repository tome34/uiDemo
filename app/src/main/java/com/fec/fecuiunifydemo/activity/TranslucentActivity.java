package com.fec.fecuiunifydemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.topbar.TopBarLayout;
import com.fec.view.common.utils.StatusBarHelper;

/**
 * 沉浸式状态栏的调用示例。
 * Created by Kayo on 2016/12/12.
 */

public class TranslucentActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    TopBarLayout mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarHelper.translucent(this); // 沉浸式状态栏
        View root = LayoutInflater.from(this).inflate(R.layout.activity_translucent, null);
        ButterKnife.bind(this, root);
        initTopBar();
        setContentView(root);
        findViewById(R.id.btn2).setOnClickListener(v -> StatusBarHelper.setStatusBarDarkMode(this));
        findViewById(R.id.btn1).setOnClickListener(v -> StatusBarHelper.setStatusBarLightMode(this));
        TextView textView = findViewById(R.id.tv);
        textView.setText("状态栏高度:"+StatusBarHelper.getStatusbarHeight(this)+"px");
    }

    private void initTopBar() {
        mTopBar.setBackgroundColor(Color.parseColor("#9FD661"));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
            }
        });

        mTopBar.setTitle("沉浸式状态栏示例");
        mTopBar.addLeftBackImageButton();
    }
}
