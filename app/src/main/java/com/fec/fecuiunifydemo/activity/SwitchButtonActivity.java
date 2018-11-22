package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.topbar.TopBar;

public class SwitchButtonActivity extends AppCompatActivity {

    private com.example.switchbutton.SwitchButton mSwitchButton;
    private TopBar mTopBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_button);
        mSwitchButton = (com.example.switchbutton.SwitchButton)findViewById(R.id.switch_button);
        mTopBar = (TopBar)findViewById(R.id.topbar);
        initTopBar();

        //开关切换事件
        mSwitchButton.setOnToggleChanged(new com.example.switchbutton.SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on){
                    Toast.makeText(SwitchButtonActivity.this, "开", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SwitchButtonActivity.this, "关", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //切换开关
       // mSwitchButton.toggle();
        //切换无动画
       // mSwitchButton.toggle(false);
       // mSwitchButton.setToggleOn();
        //mSwitchButton.setToggleOff();
        //无动画切换
       // mSwitchButton.setToggleOn(false);
       // mSwitchButton.setToggleOff(false);

        //禁用动画
       // mSwitchButton.setAnimate(false);
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("Swich开关");
    }
}
