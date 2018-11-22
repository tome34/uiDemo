package com.fec.view.common.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fec.view.common.R;
import com.fec.view.common.layout.AlphaImageButton;

/**
 * 这是一个对 {@link TopBarLayout} 的再包装,支持设置图片背景
 *
 *
 * @author XQ Young
 * @date 2018-7-23 19:31:10
 */

public class ImgTopBarLayout extends FrameLayout {
    private TopBarLayout mTopBar;
    private ImageView mImageView;

    public ImgTopBarLayout(Context context) {
        this(context, null);
    }

    public ImgTopBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_img_top_bar, this, true);
        mTopBar = findViewById(R.id.inner_tbl);
        mImageView = findViewById(R.id.inner_iv);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImgTopBarLayout);
        int resourceId = array.getResourceId(R.styleable.ImgTopBarLayout_bg_img, Integer.MIN_VALUE);
        if (resourceId != Integer.MIN_VALUE) {
            mImageView.setImageResource(resourceId);
        }
        array.recycle();
    }

    public TopBarLayout getTopBar() {
        return mTopBar;
    }


    public ImageView getImageView() {
        return mImageView;
    }

    public void setCenterView(View view) {
        mTopBar.setCenterView(view);
    }

    public TextView setTitle(int resId) {
        return mTopBar.setTitle(resId);
    }

    public TextView setTitle(String title) {
        return mTopBar.setTitle(title);
    }

    public TextView setEmojiTitle(String title) {
        return mTopBar.setEmojiTitle(title);
    }



    public void setSubTitle(int resId) {
        mTopBar.setSubTitle(resId);
    }

    public void setSubTitle(String subTitle) {
        mTopBar.setSubTitle(subTitle);
    }

    public void setTitleGravity(int gravity) {
        mTopBar.setTitleGravity(gravity);
    }

    public void addLeftView(View view, int viewId) {
        mTopBar.addLeftView(view, viewId);
    }

    public void addLeftView(View view, int viewId, RelativeLayout.LayoutParams layoutParams) {
        mTopBar.addLeftView(view, viewId, layoutParams);
    }

    public void addRightView(View view, int viewId) {
        mTopBar.addRightView(view, viewId);
    }

    public void addRightView(View view, int viewId, RelativeLayout.LayoutParams layoutParams) {
        mTopBar.addRightView(view, viewId, layoutParams);
    }

    public AlphaImageButton addRightImageButton(int drawableResId, int viewId) {
        return mTopBar.addRightImageButton(drawableResId, viewId);
    }

    public AlphaImageButton addLeftImageButton(int drawableResId, int viewId) {
        return mTopBar.addLeftImageButton(drawableResId, viewId);
    }

    public Button addLeftTextButton(int stringResId, int viewId) {
        return mTopBar.addLeftTextButton(stringResId, viewId);
    }

    public Button addLeftTextButton(String buttonText, int viewId) {
        return mTopBar.addLeftTextButton(buttonText, viewId);
    }

    public Button addRightTextButton(int stringResId, int viewId) {
        return mTopBar.addRightTextButton(stringResId, viewId);
    }

    public Button addRightTextButton(String buttonText, int viewId) {
        return mTopBar.addRightTextButton(buttonText, viewId);
    }

    public AlphaImageButton addLeftBackImageButton() {
        return mTopBar.addLeftBackImageButton();
    }

    public void removeAllLeftViews() {
        mTopBar.removeAllLeftViews();
    }

    public void removeAllRightViews() {
        mTopBar.removeAllRightViews();
    }

    public void removeCenterViewAndTitleView() {
        mTopBar.removeCenterViewAndTitleView();
    }

    /**
     * 设置 TopBar 背景的透明度
     *
     * @param alpha 取值范围：[0, 255]，255表示不透明
     */
    public void setBackgroundAlpha(int alpha) {
        mTopBar.setBackgroundAlpha(alpha);
    }

}
