package com.fec.view.common.popupWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.fec.view.common.layout.WrapContentListView;

/**
 * 继承自 {@link PopupView}，在 {@link PopupView} 的基础上，支持显示一个列表。
 *
 * @author cginechen
 * @date 2016-11-16
 */

public class ListPopup extends PopupView {
    private BaseAdapter mAdapter;

    /**
     * 构造方法。
     *
     * @param context   传入一个 Context。
     * @param direction Popup 的方向，为 {@link PopupView#DIRECTION_NONE}, {@link PopupView#DIRECTION_TOP} 和 {@link PopupView#DIRECTION_BOTTOM} 中的其中一个值。
     * @param adapter   列表的 Adapter
     */
    public ListPopup(Context context, @Direction int direction, BaseAdapter adapter) {
        super(context, direction);
        mAdapter = adapter;
    }

    public void create(int width, int maxHeight, AdapterView.OnItemClickListener onItemClickListener) {
        ListView listView = new WrapContentListView(mContext, maxHeight);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, maxHeight);
        listView.setLayoutParams(lp);
        listView.setAdapter(mAdapter);
        listView.setVerticalScrollBarEnabled(false);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setDivider(new ColorDrawable(Color.GRAY));
        listView.setDividerHeight(1);
        setContentView(listView);
    }
}
