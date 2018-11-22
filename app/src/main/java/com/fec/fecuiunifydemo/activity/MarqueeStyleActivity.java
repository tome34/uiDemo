package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.fec.fecuiunifydemo.R;
import com.fec.fecuiunifydemo.widget.WeakHandler;
import com.fec.view.common.marquee.SimpleMF;
import com.fec.view.common.marquee.SimpleMarqueeView;
import com.fec.view.common.marquee.listener.OnItemClickListener;
import com.fec.view.common.marquee.v2.TipView;
import com.fec.view.common.topbar.TopBar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MarqueeStyleActivity extends AppCompatActivity {

    private static final String TIP_PREFIX = "this is tip No.";
    private SimpleMarqueeView<String> marqueeView1, marqueeView2;
    private List<String> mDatas;
    private WeakHandler mHandler = new WeakHandler();
    TipView marqueeView3;
    TipView marqueeView4;
    TextView marqueeView5;
    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_style);
        mDatas = Arrays.asList("记录一下，以备查阅","测试测试测试测试","跑马灯效果","abcdy");
        mTopBar = (TopBar)findViewById(R.id.topbar);
        marqueeView1 = findViewById(R.id.marqueeView1);
        marqueeView2 = findViewById(R.id.marqueeView2);
        marqueeView3 = findViewById(R.id.tip_view1);
        marqueeView4 = findViewById(R.id.tip_view2);
        marqueeView5 = findViewById(R.id.tv);

        initTopBar();
        initMarqueeView1();
        initMarqueeView2();
        initMarqueeView3();
        initMarqueeView4();
        initMarqueeView5();


    }

    private void initMarqueeView5() {
        marqueeView5.setText("我是单行跑马灯效果,测试测试中!");
    }

    private void initMarqueeView1() {
        SimpleMF<String> marqueeFactory = new SimpleMF(MarqueeStyleActivity.this);
        marqueeFactory.setData(mDatas);
        marqueeView1.setMarqueeFactory(marqueeFactory);
        marqueeView1.startFlipping();
        marqueeView1.setOnItemClickListener(onSimpleItemClickListener);
    }

    private void initMarqueeView2() {
        final SimpleMF<String> marqueeFactory = new SimpleMF<>(this);
        marqueeFactory.setData(mDatas);
        marqueeView2.setMarqueeFactory(marqueeFactory);
        marqueeView2.startFlipping();
        marqueeView2.setOnItemClickListener(onSimpleItemClickListener);

        //测试重置数据效果
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int delayMillis = (random.nextInt(5) + 4) * 1000;
                marqueeFactory.setData(mDatas);
                mHandler.postDelayed(this, delayMillis);
            }
        }, 8000);
    }

    private void initMarqueeView3() {
        marqueeView3.setTipList(generateTips());
    }

    private void initMarqueeView4() {
        marqueeView4.setTipList(generateTips());
    }
    private List<String> generateTips() {
        List<String> tips = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            tips.add(TIP_PREFIX + i);
        }
        return tips;
    }

    @Override
    public void onStart() {
        super.onStart();
       // marqueeView1.startFlipping();
       // marqueeView2.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
       // marqueeView1.stopFlipping();
       // marqueeView2.stopFlipping();
    }

    private OnItemClickListener<TextView, String> onSimpleItemClickListener = new OnItemClickListener<TextView, String>() {
        @Override
        public void onItemClickListener(TextView mView, String mData, int mPosition) {
            Toast.makeText(MarqueeStyleActivity.this, String.format("mPosition:%s,mData:%s,mView:%s,.", mPosition, mData, mView), Toast.LENGTH_SHORT).show();
        }
    };

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("跑马灯效果");
    }
}
