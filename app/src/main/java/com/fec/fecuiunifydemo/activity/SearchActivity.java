package com.fec.fecuiunifydemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fec.fecuiunifydemo.R;
import com.fec.view.common.searchHistory.SearchHistoryFragment;
import com.fec.view.common.searchHistory.SearchHistoryHandler;
import java.util.Arrays;
import java.util.List;

/**
 * 商品搜索页面
 * Created by Jiangwb on 2017/12/8.
 */

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.tv_keyword)
    EditText  tvKeyvord;
    @BindView(R.id.mall_iv_clear)
    ImageView iv_clear;

    private List<String> hotStr = Arrays.asList("java,kotlin,gradle,android,ios,groovy,c,c++,python,dart,flutter".split(","));

    private SearchHistoryFragment mShf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SearchHistoryHandler.INSTANCE.init(this,"userName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_search);
        ButterKnife.bind(this);
        mShf = (SearchHistoryFragment)getSupportFragmentManager().findFragmentById(R.id.shf);
        findViewById(R.id.mall_iv_close).setOnClickListener(v -> finish());
        tvKeyvord.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchClick();
                return true;
            }
            return false;
        });

        mShf.setHotHistoryList(SearchActivity.this.hotStr);
        mShf.setOnSearchTagClick(s -> {
            tvKeyvord.setText(s);
            tvKeyvord.setSelection(s.length());
            return null;
        });
        iv_clear.setOnClickListener(v -> tvKeyvord.setText(""));

        tvKeyvord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    iv_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }





    private void gotoGoodList(String searchKeyword) {
        //startActivity(new Intent(mActivity, MallListOfGoodsActivity.class).putExtra("keyword", searchKeyword));
    }



    @OnClick(R.id.mall_image_search)
    public void searchClick() {
        String keyword = tvKeyvord.getText().toString();
        mShf.put(keyword);
        gotoGoodList(keyword);
    }
}
