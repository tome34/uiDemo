package com.fec.fecuiunifydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fec.fecuiunifydemo.R;
import com.fec.fecuiunifydemo.adapter.ImageAdapter;
import com.fec.view.common.topbar.TopBar;
import java.util.ArrayList;

public class ImageListActivity extends AppCompatActivity {

    private TopBar mTopBar;
    private RecyclerView mRecyclerView;
    private ArrayList<String> mImageStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        mTopBar = (TopBar)findViewById(R.id.topbar);
        mRecyclerView = findViewById(R.id.recycler_view);
        initTopBar();
        init();
    }

    private void init() {
        mImageStr = new ArrayList<>();
        mImageStr.add("http://ww1.sinaimg.cn/large/7a8aed7bjw1exvmxmy36wj20ru114gqq.jpg");
        mImageStr.add("http://ww2.sinaimg.cn/large/c85e4a5cgw1f62hzfvzwwj20hs0qogpo.jpg");
        mImageStr.add("http://ww3.sinaimg.cn/large/610dc034jw1f5e7x5vlfyj20dw0euaax.jpg");
        mImageStr.add("http://ww4.sinaimg.cn/large/7a8aed7bjw1ez5zq5g685j20hj0qo0w1.jpg");
        mImageStr.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=722693321,3238602439&fm=27&gp=0.jpg");
        mImageStr.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524118892596&di=5e8f287b5c62ca0c813a548246faf148&imgtype=0&src=http%3A%2F%2Fwx1.sinaimg.cn%2Fcrop.0.0.1080.606.1000%2F8d7ad99bly1fcte4d1a8kj20u00u0gnb.jpg");
        mImageStr.add("http://ww4.sinaimg.cn/large/7a8aed7bjw1ez5zq5g685j20hj0qo0w1.jpg");
        mImageStr.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524118892596&di=5e8f287b5c62ca0c813a548246faf148&imgtype=0&src=http%3A%2F%2Fwx1.sinaimg.cn%2Fcrop.0.0.1080.606.1000%2F8d7ad99bly1fcte4d1a8kj20u00u0gnb.jpg");
        mImageStr.add("http://ww4.sinaimg.cn/large/7a8aed7bjw1ez5zq5g685j20hj0qo0w1.jpg");

        //设置RecyclerView管理器
        mRecyclerView.setLayoutManager(new GridLayoutManager(ImageListActivity.this, 3));
        //初始化适配器
        ImageAdapter mAdapter = new ImageAdapter(R.layout.image_item , mImageStr);
        //设置添加或删除item时的动画，这里使用默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Object item,View view,int position) {
                Intent intent = new Intent(ImageListActivity.this, PhotoViewActivity.class);
                intent.putStringArrayListExtra("imageList", mImageStr);
                intent.putExtra("position", position );
                startActivity(intent);
            }
        });
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("图片浏览");
    }
}
