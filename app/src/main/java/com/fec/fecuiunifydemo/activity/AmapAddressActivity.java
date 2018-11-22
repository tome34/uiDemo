package com.fec.fecuiunifydemo.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fec.fecuiunifydemo.R;
import com.fec.fecuiunifydemo.adapter.PoiAdapter;
import com.fec.fecuiunifydemo.bean.PoiBean;
import com.fec.view.common.topbar.TopBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;

public class AmapAddressActivity extends AppCompatActivity implements AMap.OnMapClickListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter, PoiSearch.OnPoiSearchListener,
    OnLoadMoreListener, OnRefreshListener, LocationSource, AMapLocationListener, AMap.OnCameraChangeListener {

    @BindView(R.id.mall_iv_close)
    ImageButton mMallIvClose;
    @BindView(R.id.tv_keyword)
    TextView mTvKeyword;
    @BindView(R.id.mall_iv_clear)
    ImageView mMallIvClear;
    @BindView(R.id.mall_image_search)
    ImageView mMallImageSearch;
    @BindView(R.id.layout_search)
    LinearLayout mLayoutSearch;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.topbar)
    TopBar mTopbar;
    @BindView(R.id.location_tv)
    TextView mLocationTv;

    //定位类
    private AMapLocationClient locationClient = null;
    private AMap mAMap;
    private List<PoiBean> poiData;
    private PoiAdapter mAdapter;
    //首次进入定位成功信息
    private AMapLocation mLoc;
    private LatLonPoint mLp;
    //定位的经纬度
    private double mLatitude, mLongitude;
    // Poi查询条件类
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    //页数从第0页开始
    private int currentPage = 0;
    // 选择的点
    private Marker locationMarker;
    //是否为搜索的结果
    private boolean isSearch;
    //定位的城市
    private String mCity;
    //地图搜索周边bean
    private PoiBean mCurrPoiBean;
    //搜索结果得到
    private PoiItem searchPonItem;

    private static final int REQUEST_SEARCH_CODE = 1;
    private static final int RESULT_INTENT_CODE=2;

    private UiSettings mUiSettings;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap_address);
        ButterKnife.bind(this);
        initTopBar();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        init();
        //初始化定位
        InitLocation();
    }

    private void init() {

        poiData = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PoiAdapter(this,poiData);
        mRecyclerView.setAdapter(mAdapter);
        //刷新监听
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener<PoiBean>() {
            @Override
            public void onItemClick(PoiBean item,View view,int position) {
                Log.d("条目点击事件","" + position +","+mCurrPoiBean.isLoc());
                //PoiBean poiBean = poiData.get((int) position);
                //mCurrPoiBean=poiBean;
                //LatLonPoint point = poiBean.getPoint();
                //addmark(point.getLatitude(),point.getLongitude());
                //mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(point.getLatitude(), point.getLongitude()), 14));
                //Log.d("Marker","0click:"+poiData.size());
                //for (int i=0;i<poiData.size();i++){
                //    poiData.get(i).setSelected(false);
                //}
                //poiBean.setSelected(true);
                //mAdapter.notifyDataSetChanged();

                //非搜索
                if (mCurrPoiBean.isLoc()){
                    Toast.makeText(AmapAddressActivity.this,"详细地址："+mCurrPoiBean.getLocAddress()+"\n经度："+mLongitude+"\n纬度："+mLatitude,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AmapAddressActivity.this,"详细地址："+mCurrPoiBean.getAd() + mCurrPoiBean.getSnippet()+"\n经度："+mLongitude+"\n纬度："+mLatitude,Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }

    private void InitLocation() {
        //初始化经纬度
        mLp = new LatLonPoint(39.907775,116.247522);
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        locationClient.startLocation();
    }

    /**
     * 显示地图
     */
    private void initMap(AMapLocation loc) {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            //设置地图ui
            mUiSettings = mAMap.getUiSettings();
            SettingMapUI();
            //设置地图点击事件监听接口
            mAMap.setOnMapClickListener(this);
            //点标记用来在地图上标记任何位置
            mAMap.setOnMarkerClickListener(this);
            //设置一个个性化渲染的内容的信息窗口
            mAMap.setInfoWindowAdapter(this);
            //定义 Marker拖拽的监听
           // mAMap.setOnMarkerDragListener(this);
            //设置地图拖动监听
            mAMap.setOnCameraChangeListener(this);
            //定位
            locationMarker = mAMap.addMarker(new MarkerOptions().anchor(0.5f,0.5f).icon(
                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.poi_marker_1))).position(new LatLng(loc.getLatitude(),loc.getLongitude()))
                                                                //设置Marker可拖动
                                                                .draggable(false));
        }

        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(),loc.getLongitude()),14));
    }

    /**
     * 设置地图ui
     */
    private void SettingMapUI() {
        // 设置地图logo显示在右下方
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        // 设置地图默认的缩放按钮是否显示
        mUiSettings.setZoomControlsEnabled(false);
        // 设置定位监听
        mAMap.setLocationSource(this);
        // 是否显示默认的定位按钮
        mUiSettings.setMyLocationButtonEnabled(true);
        // 是否可触发定位并显示定位层
        mAMap.setMyLocationEnabled(true);

        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(
            BitmapDescriptorFactory.fromResource(R.mipmap.ic_location2));
        // 将自定义的 myLocationStyle 对象添加到地图上
        mAMap.setMyLocationStyle(myLocationStyle);
    }

    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setGpsFirst(false);
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setHttpTimeOut(30000);
        //可选，设置定位间隔。默认为2秒
        mOption.setInterval(2000);
        //可选，设置是否返回逆地理地址信息。默认是true
        mOption.setNeedAddress(true);
        //可选，设置是否单次定位。默认是false
        mOption.setOnceLocation(true);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.setOnceLocationLatest(false);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //可选，设置是否使用传感器。默认是false
        mOption.setSensorEnable(false);
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setWifiScan(true);
        //可选，设置是否使用缓存定位，默认为true
        mOption.setLocationCacheEnable(false);
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                mCity = loc.getCity();
                Log.e("yufs","当前经度" + loc.getLongitude() + "当前维度：" + loc.getLatitude());
                mLoc = loc;
                mLp.setLongitude(loc.getLongitude());
                mLp.setLatitude(loc.getLatitude());
                //得到定位信息
                Log.e("yufs","定位详细信息：" + loc.toString());
                mLatitude = loc.getLatitude();
                mLongitude = loc.getLongitude();
                //初始化地图对象
                initMap(loc);
                //查询周边
                doSearchQuery(loc.getCity(),loc.getLatitude(),loc.getLongitude());
            } else {
                Toast.makeText(AmapAddressActivity.this,"定位失败，请打开位置权限",Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 点击
     * @param view
     */
    @OnClick( { R.id.tv_keyword,R.id.layout_search })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_keyword:
                mIntent = new Intent(this,SearchAddressActivity.class);
                mIntent.putExtra("city", mCity);
                startActivityForResult(mIntent,REQUEST_SEARCH_CODE);
                break;
            case R.id.layout_search:
                mIntent = new Intent(this,SearchAddressActivity.class);
                mIntent.putExtra("city", mCity);
                startActivityForResult(mIntent,REQUEST_SEARCH_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(requestCode==REQUEST_SEARCH_CODE && resultCode== 6){
            searchPonItem=data.getParcelableExtra("poiItem");
            String title = searchPonItem.getTitle();
            LatLonPoint latLonPoint = searchPonItem.getLatLonPoint();
            //移动标志和地图
            addmark(latLonPoint.getLatitude(),latLonPoint.getLongitude());
           // mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()), 14));
            //重新搜索附近
            //数据清空
            isSearch=true;
            poiData.clear();
            currentPage=0;
            String cityCode=searchPonItem.getCityCode();
            mCity = searchPonItem.getCityName();
            mLatitude=latLonPoint.getLatitude();
            mLongitude=latLonPoint.getLongitude();
            doSearchQuery(searchPonItem.getCityName(),latLonPoint.getLatitude(), latLonPoint.getLongitude());
        }
        super.onActivityResult(requestCode,resultCode,data);

    }

    /**
     * 开始进行poi搜索周围
     */
    protected void doSearchQuery(String city,double latitude,double longitude) {
        Log.d("Marker","9click:"+city +","+(mLp != null));
        String mType = "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施";
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query("",mType,city);
        // 设置每页最多返回多少条poiitem
        query.setPageSize(20);
        // 设置查第一页
        query.setPageNum(currentPage);
        //城市
        if (!TextUtils.isEmpty(city)){
            mLocationTv.setText(city);
        }
        if (mLp != null) {
            poiSearch = new PoiSearch(this,query);
            //搜索的监听,并返回结果
            poiSearch.setOnPoiSearchListener(this);
            //以当前定位的经纬度为准搜索周围5000米范围
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude,longitude),2000,true));
            // 异步搜索
            poiSearch.searchPOIAsyn();
        }
    }

    //移动图标
    private void addmark(double latitude, double longitude) {

        if (locationMarker == null) {
            locationMarker = mAMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.mipmap.poi_marker_1)))
                .draggable(true));
        } else {
            locationMarker.setPosition(new LatLng(latitude, longitude));
            mAMap.invalidate();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * 搜索结果回调
     */
    @Override
    public void onPoiSearched(PoiResult poiResult,int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            // 搜索poi的结果
            if (poiResult != null && poiResult.getQuery() != null) {
                // 是否是同一条
                if (poiResult.getQuery().equals(query)) {
                    // 取得第一页的poiitem数据，页数从数字0开始
                    //如果是第一页得加入定位的这一行,并且不是搜索点击过来的
                    if (currentPage == 0 && mLoc != null && !isSearch) {
                        PoiBean firBean = new PoiBean();
                        firBean.setLoc(true);
                        firBean.setSelected(true);
                        firBean.setTitleName(mLoc.getPoiName());
                        firBean.setPoint(new LatLonPoint(mLoc.getLatitude(),mLoc.getLongitude()));
                        firBean.setLocAddress(mLoc.getAddress());
                        poiData.add(firBean);
                        mCurrPoiBean = firBean;
                    } else if (currentPage == 0 && isSearch && searchPonItem != null) {
                        PoiBean firBean = new PoiBean();
                        firBean.setTitleName(searchPonItem.getTitle());
                        firBean.setCityName(searchPonItem.getCityName());
                        firBean.setAd(searchPonItem.getAdName());
                        firBean.setSnippet(searchPonItem.getSnippet());
                        firBean.setPoint(searchPonItem.getLatLonPoint());
                        firBean.setSelected(true);
                        poiData.add(firBean);
                        mCurrPoiBean = firBean;
                    }
                    // 取得第一页的poiitem数据，页数从数字0开始
                    List<PoiItem> poiItems = poiResult.getPois();
                    // 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
                    List<PoiBean> tem = new ArrayList<>();
                    if (poiItems != null && poiItems.size() > 0) {
                        for (int j = 0; j < poiItems.size(); j++) {
                            PoiItem poiItem = poiItems.get(j);
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
                        //刷新
                        if (currentPage == 0) {
                            poiData.clear();
                        }
                        poiData.addAll(tem);
                        mAdapter.notifyDataSetChanged();
                     /*   if (isSearch){
                                moveMapCamera(poiItems.get(0).getLatLonPoint().getLatitude(),poiItems.get(0).getLatLonPoint().getLongitude());
                        }*/
                    }
                } else {
                    Toast.makeText(this,"搜索失败",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this,"搜索失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem,int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        currentPage++;
        doSearchQuery(mCity,mLatitude,mLongitude);
        mRefreshLayout.finishLoadMore(2000);
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentPage = 0;
        doSearchQuery(mCity,mLatitude,mLongitude);
        mRefreshLayout.finishRefresh(2000);
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听,定位成功的回调
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                // 显示系统小蓝点
                mListener.onLocationChanged(aMapLocation);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    private void initTopBar() {
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopbar.setTitle("选择收货地址");
    }

//---------------地图拖动监听---------------------//

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.d("Marker","7click:"+cameraPosition.toString());

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLng target = cameraPosition.target;
        Log.d("Marker","8click:"+target);
        mLatitude = target.latitude;
        mLongitude = target.longitude;
        Log.e("latitude",mLatitude+"");
        Log.e("longitude",mLongitude+"");
        addmark(mLatitude, mLongitude);
       // mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude), 14));
        doSearchQuery(mCity,mLatitude,mLongitude);
    }

}
