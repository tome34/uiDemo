package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.popupWindow.ListPopup;
import com.fec.view.common.popupWindow.PopupView;
import com.fec.view.common.topbar.TopBar;
import com.fec.view.common.utils.DisplayHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class PopupAutoActivity extends AppCompatActivity implements View.OnClickListener {

    private TopBar mTopBar;
    private Button mBtn1 ;
    private Button mBtn2 ;

    private PopupView mNormalPopup;
    private ListPopup mListPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_auto);
        mTopBar = findViewById(R.id.topbar);
        mBtn1 = findViewById(R.id.actiontBtn1);
        mBtn2 = findViewById(R.id.actiontBtn2);
        initTopBar();
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.actiontBtn1){
            initNormalPopupIfNeed();
            mNormalPopup.setAnimStyle(PopupView.ANIM_GROW_FROM_CENTER);
            mNormalPopup.setPreferredDirection(PopupView.DIRECTION_BOTTOM);
            mNormalPopup.show(v);
            mBtn1.setText("隐藏普通浮层");
        }else if (v.getId() == R.id.actiontBtn2){
            initListPopupIfNeed();
            mListPopup.setAnimStyle(PopupView.ANIM_GROW_FROM_CENTER);
            mListPopup.setPreferredDirection(PopupView.DIRECTION_TOP);
            mListPopup.show(v);
            mBtn2.setText("隐藏列表浮层");
        }
    }

    private void initNormalPopupIfNeed() {
        if (mNormalPopup == null) {
            mNormalPopup = new PopupView(PopupAutoActivity.this, PopupView.DIRECTION_NONE);
            TextView textView = new TextView(PopupAutoActivity.this);
            textView.setLayoutParams(mNormalPopup.generateLayoutParam(
                DisplayHelper.dp2px(PopupAutoActivity.this, 250),
                WRAP_CONTENT
            ));
            textView.setLineSpacing(DisplayHelper.dp2px(PopupAutoActivity.this, 4), 1.0f);
            int padding = DisplayHelper.dp2px(PopupAutoActivity.this, 20);
            textView.setPadding(padding, padding, padding, padding);
            textView.setText("Popup 可以设置其位置以及显示和隐藏的动画");
            textView.setTextColor(ContextCompat.getColor(PopupAutoActivity.this, R.color.app_color_description));
            mNormalPopup.setContentView(textView);
            mNormalPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mBtn1.setText("显示普通浮层");
                }
            });
        }
    }

    private void initListPopupIfNeed() {
        if (mListPopup == null) {

            String[] listItems = new String[]{
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4",
                "Item 5",
            };
            List<String> data = new ArrayList<>();

            Collections.addAll(data, listItems);

            ArrayAdapter adapter = new ArrayAdapter<>(PopupAutoActivity.this, R.layout.simple_list_item, data);

            mListPopup = new ListPopup(PopupAutoActivity.this, PopupView.DIRECTION_NONE, adapter);
            mListPopup.create(DisplayHelper.dp2px(PopupAutoActivity.this, 250), DisplayHelper.dp2px(PopupAutoActivity.this, 200), new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(PopupAutoActivity.this, "Item " + (i + 1), Toast.LENGTH_SHORT).show();
                    mListPopup.dismiss();
                }
            });
            mListPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mBtn2.setText("显示列表浮层");
                }
            });
        }
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("浮层弹框");
    }


}
