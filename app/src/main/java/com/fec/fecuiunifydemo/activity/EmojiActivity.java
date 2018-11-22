package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;
import com.fec.fecuiunifydemo.R;
import com.fec.fecuiunifydemo.adapter.ChattingListAdapter;
import com.fec.view.common.topbar.TopBar;
import com.sj.emoji.EmojiBean;
import java.util.ArrayList;
import java.util.List;
import sj.keyboard.XhsEmoticonsKeyBoard;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.data.ImMsgBean;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.utils.SimpleCommonUtils;
import sj.keyboard.widget.Constants;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.FuncLayout;

public class EmojiActivity extends AppCompatActivity implements EmoticonsEditText.OnSizeChangedListener, View.OnClickListener, AbsListView.OnScrollListener,
    FuncLayout.OnFuncKeyBoardListener {

    private TopBar mTopBar;
    private XhsEmoticonsKeyBoard ekBar ;
    private ListView mLvChat ;

    private ChattingListAdapter chattingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);
        mTopBar = findViewById(R.id.topbar);
        ekBar = findViewById(R.id.ek_bar);
        mLvChat = findViewById(R.id.lv_chat);
        initTopBar();
        init();
    }

    private void init() {
        initEmoticonsKeyBoardBar();
        initRecyclerView();
    }


    private void initEmoticonsKeyBoardBar() {

        SimpleCommonUtils.initEmoticonsEditText(ekBar.getEtChat());
        ekBar.setAdapter(SimpleCommonUtils.getCommonAdapter(this, emoticonClickListener));

        ekBar.getEtChat().setOnSizeChangedListener(this);
        //发送
        ekBar.getBtnSend().setOnClickListener(this);
        //添加表情库
        ekBar.getEmoticonsToolBarView().addFixedToolItemView(false, R.mipmap.icon_add1,null,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmojiActivity.this, "ADD", Toast.LENGTH_SHORT).show();
            }
        });
        //添加设置
        ekBar.getEmoticonsToolBarView().addToolItemView(R.mipmap.icon_setting,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmojiActivity.this, "SETTING", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initRecyclerView() {
        chattingListAdapter = new ChattingListAdapter(this);
        List<ImMsgBean> beanList = new ArrayList<>();
        chattingListAdapter.addData(beanList);
        mLvChat.setAdapter(chattingListAdapter);

        mLvChat.setOnScrollListener(this);

    }

    /**
     * 表情点击事件
     */
    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick(ekBar.getEtChat());
            } else {
                if(o == null){
                    return;
                }
                if(actionType == Constants.EMOTICON_CLICK_BIGIMAGE){
                    if(o instanceof EmoticonEntity){
                        OnSendImage(((EmoticonEntity)o).getIconUri());
                    }
                } else {
                    String content = null;
                    if(o instanceof EmojiBean){
                        content = ((EmojiBean)o).emoji;
                    } else if(o instanceof EmoticonEntity){
                        content = ((EmoticonEntity)o).getContent();
                    }

                    if(TextUtils.isEmpty(content)){
                        return;
                    }
                    int index = ekBar.getEtChat().getSelectionStart();
                    Editable editable = ekBar.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);

    }

    private void OnSendImage(String image) {
        if (!TextUtils.isEmpty(image)) {
            OnSendBtnClick("[img]" + image);
        }
    }

    private void OnSendBtnClick(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ImMsgBean bean = new ImMsgBean();
            bean.setContent(msg);
            chattingListAdapter.addData(bean, true, false);
            scrollToBottom();
        }
    }

    private void scrollToBottom() {
        mLvChat.requestLayout();
        mLvChat.post(new Runnable() {
            @Override
            public void run() {
                mLvChat.setSelection(mLvChat.getBottom());
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
        mTopBar.setTitle("表情键盘");
    }


    @Override
    public void onSizeChanged(int w,int h,int oldw,int oldh) {
        scrollToBottom();
    }

    @Override
    public void onClick(View v) {
        OnSendBtnClick(ekBar.getEtChat().getText().toString());
        ekBar.getEtChat().setText("");
    }

    /**
     * listview 滑动监听
     * @param view
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view,int scrollState) {
        switch (scrollState){
            case SCROLL_STATE_FLING:
                break;
            case SCROLL_STATE_IDLE:
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                ekBar.reset();
                break;
            default:

        }
    }

    @Override
    public void onScroll(AbsListView view,int firstVisibleItem,int visibleItemCount,int totalItemCount) {

    }

    @Override
    public void OnFuncPop(int height) {
        scrollToBottom();
    }

    @Override
    public void OnFuncClose() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        ekBar.reset();
    }
}
