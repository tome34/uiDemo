package com.fec.fecuiunifydemo.command;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.popupWindow.v2.CustomPopWindow;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/13  15:00
 * @describe ${popup底部窗口}
 */
public class PopupWindow implements ICommand, View.OnClickListener {

    private CustomPopWindow mCustomPopWindow;

    @Override
    public void execute(@NotNull Context context,int position) {
        Activity activity = (Activity)context;
        FrameLayout mLayoutPopup = (FrameLayout)activity.findViewById(R.id.layout_frame);

        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_product_detail, null);
        TextView tv = contentView.findViewById(R.id.tv_pop);
        tv.setOnClickListener(this);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow
            .PopupWindowBuilder(context)
            .setView(contentView)
            .enableBackgroundDark(true)
            .setBgDarkAlpha(0.7f)
            .setAnimationStyle(R.style.pop_bottom_anim)
            .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            .create().showAtLocation(mLayoutPopup,Gravity.BOTTOM,0,0);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_pop){
            mCustomPopWindow.dissmiss();
        }
    }
}
