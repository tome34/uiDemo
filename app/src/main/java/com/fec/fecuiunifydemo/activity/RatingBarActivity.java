package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.RatingBar;
import com.fec.view.common.topbar.TopBar;

/**
 * @author tome
 * @date 2018/7/12  17:18
 * @describe
 */
public class RatingBarActivity  extends AppCompatActivity {

    private RatingBar mRatingBar;
    private TopBar mTopBar;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);
        mRatingBar = (RatingBar) findViewById(R.id.ratingbar_v);
        mTextView = (TextView) findViewById(R.id.number);
        mTopBar = (TopBar)findViewById(R.id.topbar);
        initTopBar();
        initView();
    }

    private void initView() {

        mRatingBar.setStarMark(3.5f);
        mRatingBar.setIntegerMark(true);
        mRatingBar.setOnStarChangeListener(new RatingBar.OnStarChangeListener() {
            @Override
            public void onStarChange(float mark) {
                mTextView.setText(mark +"");
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
        mTopBar.setTitle("星星");
    }
}
