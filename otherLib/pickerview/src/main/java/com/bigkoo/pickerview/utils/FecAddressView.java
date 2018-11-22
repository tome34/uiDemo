package com.bigkoo.pickerview.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述    :
 * 包名      : com.fecmobile.dididingshuimo.utils
 * 创建人    : zhangh
 * 创建时间  : 2018/1/15  20:47
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class FecAddressView {

    private final Context                             context;
    private       List<AddressSelectBean>             mProvince;
    private       List<List<AddressSelectBean>>       mCity;
    private       List<List<List<AddressSelectBean>>> mArea;

    private OptionsPickerView       pickerView;
    private OnAddressSelectListener listener;

    private String               selectedProv;
    private String               selectedCity;
    private String               selectedArea;
    private OptionsPickerBuilder builder;

    public FecAddressView(Context context) {
        this.context = context;
    }

    public static FecAddressView getInstance(Context context) {
        return new FecAddressView(context);
    }

    /**
     * 设置选中的地区
     * @param userProv 选中地区的名字
     * @param userCity
     * @param userArea
     * @return
     */
    public FecAddressView setSelected(String userProv, String userCity, String userArea) {
        selectedProv = userProv;
        selectedCity = userCity;
        selectedArea = userArea;
        return this;
    }

    public FecAddressView setOptionsBuild(OptionsPickerBuilder build) {
        this.builder = build;
        return this;
    }

    public FecAddressView build() {
        mCity = new ArrayList<List<AddressSelectBean>>();
        mArea = new ArrayList<List<List<AddressSelectBean>>>();
        //确定按钮文字
        OnOptionsSelectListener listener = new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                AddressSelectBean province;
                if (mProvince != null && mProvince.size() > options1) {
                    province = mProvince.get(options1);
                } else {
                    province = new AddressSelectBean();
                }
                AddressSelectBean city;
                if (mCity != null && mCity.size() > options1 && mCity.get(options1).size() > options2) {
                    city = mCity.get(options1).get(options2);
                } else {
                    city = new AddressSelectBean();
                }
                AddressSelectBean area;
                if (mArea != null && mArea.size() > options1 && mArea.get(options1).size() > options2 && mArea.get(options1).get(options2).size() > options3) {
                    area = mArea.get(options1).get(options2).get(options3);
                } else {
                    area = new AddressSelectBean();
                }
                if (FecAddressView.this.listener != null) {
                    FecAddressView.this.listener.getAddressInfo(province, city, area);
                }
            }
        };//context.getResources().getColor(R.color.textColor_alert_button_cancel)
        if (builder == null) {
            builder = new OptionsPickerBuilder(context, listener).setSubmitText("确定")//确定按钮文字
                                                                 .setCancelText("取消")//取消按钮文字
                                                                 .setTitleText("城市选择")//标题
                                                                 .setSubCalSize(18)//确定和取消文字大小
                                                                 .setTitleSize(20)//标题文字大小
                                                                 .setTitleColor(Color.BLACK)//标题文字颜色
                                                                 .setSubmitColor(context.getResources().getColor(R.color.alert_button_destructive))//确定按钮文字颜色
                                                                 .setCancelColor(context.getResources().getColor(R.color.alert_button_cancel))//取消按钮文字颜色
                                                                 .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                                                                 .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                                                                 .setContentTextSize(18)//滚轮文字大小
                                                                 //                .setLabels("省", "市", "区")//设置选择的三级单位
                                                                 .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                                                                 .setCyclic(false, false, false)//循环与否
                                                                 //.setSelectOptions(1, 1, 1)  //设置默认选中项
                                                                 .setOutSideCancelable(false)//点击外部dismiss default true
                                                                 .isDialog(false);//是否显示为对话框样式
        } else {
            builder = new OptionsPickerBuilder(this.builder).setOnOptionsSelectListener(listener).setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                                                            .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                                                            .setContentTextSize(18)//滚轮文字大小
                                                            //                .setLabels("省", "市", "区")//设置选择的三级单位
                                                            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                                                            .setCyclic(false, false, false)//循环与否
                                                            //.setSelectOptions(1, 1, 1)  //设置默认选中项
                                                            .setOutSideCancelable(false)//点击外部dismiss default true
                                                            .isDialog(false);//是否显示为对话框样式;
        }

        pickerView = builder.build();
        int selectedP = 0, selectedC = 0, selectedA = 0;
        boolean b1 = false, b2 = false, b3 = false;
        mProvince = new Gson().fromJson(getJson(context, "address_ios.txt"), new TypeToken<ArrayList<AddressSelectBean>>() {
        }.getType());
        for (int i = 0; i < mProvince.size(); i++) {
            List<List<AddressSelectBean>> mAreaData = new ArrayList<List<AddressSelectBean>>();
            AddressSelectBean addressBean = mProvince.get(i);
            if (!TextUtils.isEmpty(selectedProv) && !b1 && selectedProv.contains(addressBean.getName())) {
                selectedP = i;
                b1 = true;
            }
            List<AddressSelectBean> chindren = addressBean.getChindren();
            if (chindren == null) {
                mCity.add(new ArrayList<AddressSelectBean>());
                mAreaData.add(new ArrayList<AddressSelectBean>());
            } else {
                mCity.add(chindren);
                for (int j = 0; j < chindren.size(); j++) {
                    AddressSelectBean city = chindren.get(j);
                    if (!TextUtils.isEmpty(selectedCity) && !b2 && selectedCity.contains(city.getName())) {
                        selectedC = j;
                        b2 = true;
                    }
                    List<AddressSelectBean> areaList = city.getChindren();
                    mAreaData.add(areaList);
                    if (!TextUtils.isEmpty(selectedArea) && !b3 && areaList != null) {
                        for (int n = 0; n < areaList.size(); n++) {
                            if (selectedArea.contains(areaList.get(n).getName())) {
                                selectedA = n;
                                b3 = true;
                                break;
                            }
                        }
                    }
                }
            }
            mArea.add(mAreaData);
        }
        pickerView.setSelectOptions(selectedP, selectedC, selectedA);
        pickerView.setPicker(mProvince, mCity, mArea);
        return this;
    }

    public String getJson(Context context, String fileName) {

        AssetManager assets = context.getAssets();
        String text = "";
        try {
            InputStream is = assets.open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer, "GB2312");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void show() {
        if (pickerView != null) {
            //pickerView.setSelectOptions(0,0,0);
            pickerView.show();
        }
    }

    public FecAddressView setSelectListener(OnAddressSelectListener listener) {
        this.listener = listener;
        return this;
    }

    @FunctionalInterface
    public interface OnAddressSelectListener {
        void getAddressInfo(AddressSelectBean province, AddressSelectBean city, AddressSelectBean area);
    }
}
