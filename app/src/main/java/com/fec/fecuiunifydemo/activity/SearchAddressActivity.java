package com.fec.fecuiunifydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fec.fecuiunifydemo.R;
import com.fec.fecuiunifydemo.adapter.PoiAdapter;
import com.fec.fecuiunifydemo.bean.PoiBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;

public class SearchAddressActivity extends AppCompatActivity implements PoiSearch.OnPoiSearchListener, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.mall_iv_close)
    ImageButton mMallIvClose;
    //@BindView(R.id.tv_keyword)
    //EditText mTvKeyword;
    @BindView(R.id.mall_iv_clear)
    ImageView mMallIvClear;
    @BindView(R.id.mall_image_search)
    ImageView mMallImageSearch;
    @BindView(R.id.layout_search)
    LinearLayout mLayoutSearch;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_no_data)
    TextView mTvNoData;
    @BindView(R.id.ll_loading)
    LinearLayout mLlLoading;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.input_edittext)
    AutoCompleteTextView mInputEdittext;

    private int currentPage = 0;
    // Poi查询条件类
    private PoiSearch.Query query;
    // POI搜索
    private PoiSearch poiSearch;

    private List<PoiBean> poiData;
    private List<PoiItem> savePoiItem;
    private PoiAdapter mAdapter;
    private String mKeyWord;
    private String mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        poiData = new ArrayList<>();
        savePoiItem = new ArrayList<>();
        Intent intent = getIntent();
        mCity = intent.getStringExtra("city");
        Log.d("搜索","" + mCity);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PoiAdapter(this,poiData);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener<PoiBean>() {
            @Override
            public void onItemClick(PoiBean item,View view,int position) {
                PoiItem poiItem = savePoiItem.get((int)position);
                Intent intent = new Intent();
                intent.putExtra("poiItem",poiItem);
                setResult(6,intent);
                finish();
            }
        });

        mInputEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v,int actionId,KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d("点击了搜索","");
                    search();
                    return true;
                }
                return false;
            }
        });

        mInputEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after) {

            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count) {
                mKeyWord = s.toString().trim();
                if (mKeyWord != null && mKeyWord.length() > 0) {
                    mMallIvClear.setVisibility(View.VISIBLE);
                } else {
                    mMallIvClear.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                search();
            }
        });
    }

    @OnClick( { R.id.mall_iv_close,R.id.mall_iv_clear,R.id.mall_image_search })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mall_iv_close:
                finish();
                break;
            case R.id.mall_iv_clear:
                mInputEdittext.setText("");
                break;
            //搜索
            case R.id.mall_image_search:
                search();
                break;
        }
    }

    public void search() {
        mKeyWord = mInputEdittext.getText().toString().trim();
        if (TextUtils.isEmpty(mKeyWord)) {
            Toast.makeText(this,"请输入您要查找的地点",Toast.LENGTH_SHORT).show();
            return;
        }
        poiData.clear();
        currentPage = 0;
        doSearchQuery(mKeyWord);
    }

    protected void doSearchQuery(String keyWord) {
        if (currentPage == 0) {
            savePoiItem.clear();
            // 显示进度框
            mLlLoading.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keyWord,"",mCity);
        // 设置每页最多返回多少条poiitem
        query.setPageSize(20);
        // 设置查第一页
        query.setPageNum(currentPage);
        query.setCityLimit(true);
        poiSearch = new PoiSearch(this,query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 搜索回调
     */
    @Override
    public void onPoiSearched(PoiResult poiResult,int rCode) {
        Log.d("Marker","10click:" + rCode + "," + poiResult);
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            // 搜索poi的结果
            if (poiResult != null && poiResult.getQuery() != null) {
                // 是否是同一条
                if (poiResult.getQuery().equals(query)) {
                    if (currentPage == 0) {
                        // 隐藏对话框
                        mLlLoading.setVisibility(View.GONE);
                        mRefreshLayout.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mTvNoData.setVisibility(View.GONE);
                    }
                    // 取得搜索到的poiitems有多少页, 取得第一页的poiitem数据，页数从数字0开始
                    List<PoiItem> poiItems = poiResult.getPois();
                    savePoiItem.addAll(poiItems);
                    List<PoiBean> tem = new ArrayList<>();
                    if (poiItems != null && poiItems.size() > 0) {
                        for (int i = 0; i < poiItems.size(); i++) {
                            PoiItem poiItem = poiItems.get(i);
                            PoiBean bean = new PoiBean();
                            bean.setTitleName(poiItem.getTitle());
                            bean.setCityName(poiItem.getCityName());
                            bean.setAd(poiItem.getAdName());
                            bean.setSnippet(poiItem.getSnippet());
                            bean.setPoint(poiItem.getLatLonPoint());
                            Log.e("yufs","" + poiItem.getTitle() + "," + poiItem.getProvinceName() + "," + poiItem.getCityName() + "," + poiItem.getAdName() + ","//区
                                + poiItem.getSnippet() + "," + poiItem.getLatLonPoint() + "\n");
                            tem.add(bean);
                        }
                        poiData.addAll(tem);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        //没有结果, 隐藏对话框
                        mLlLoading.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.GONE);
                        mRefreshLayout.setVisibility(View.GONE);
                        mTvNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    //没有结果, 隐藏对话框
                    mLlLoading.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    mRefreshLayout.setVisibility(View.GONE);
                    mTvNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mLlLoading.setVisibility(View.GONE);
                Toast.makeText(this,"搜索失败：" + rCode,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem,int i) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentPage = 0;
        doSearchQuery(mKeyWord);
        mRefreshLayout.finishRefresh(2000);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        currentPage++;
        doSearchQuery(mKeyWord);
        mRefreshLayout.finishLoadMore(2000);
    }

}
