package com.fec.fecuiunifydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.fec.fecuiunifydemo.R;
import com.fec.fecuiunifydemo.adapter.ImagePreviewAdapter;
import com.fec.fecuiunifydemo.widget.HackyViewPager;
import com.fec.view.common.topbar.TopBar;
import java.util.ArrayList;

public class PhotoViewActivity extends AppCompatActivity implements View.OnClickListener {

    private HackyViewPager mViewPager;
    private TextView mSave;
    private TextView mPager;
    private ArrayList<String> mImageStr;
    public int currentPosit ;
    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        mViewPager = (HackyViewPager)findViewById(R.id.view_pager);
        mTopBar = (TopBar)findViewById(R.id.topbar);
        mSave = (TextView)findViewById(R.id.tv_save);
        mPager = (TextView)findViewById(R.id.tv_pager);
        mSave.setOnClickListener(this);
        initTopBar();
        init();
    }

    private void init() {
        mImageStr = new ArrayList<>();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        mImageStr = intent.getStringArrayListExtra("imageList");

        ImagePreviewAdapter pagerAdapter = new ImagePreviewAdapter(this, mImageStr);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(position);

        int size = mImageStr.size();
        mPager.setText((position + 1) +"/"+size);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosit = position +1 ;
                mPager.setText((position + 1) +"/"+size);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(PhotoViewActivity.this, "保存成功", Toast.LENGTH_LONG).show();
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("图片缩放");
    }
}
