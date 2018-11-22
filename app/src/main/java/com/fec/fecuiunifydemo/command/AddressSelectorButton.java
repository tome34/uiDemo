package com.fec.fecuiunifydemo.command;

import android.app.Activity;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.R;
import com.fec.view.addresswheel.ShowSelectDeepTimeUtil;
import com.fec.view.addresswheel.ShowSelectDeepUtils;
import com.fec.view.addresswheel.bean.DateTimeSelectBean;
import com.fec.view.addresswheel.widget.Selector;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/19  9:59
 * @describe ${仿京东地址选择器}
 */
public class AddressSelectorButton  implements ICommand, ShowSelectDeepTimeUtil.SelectContentListener {

    private ShowSelectDeepUtils showSelectDeepUtils;
    private String mProvince;
    private String mCity;
    private String mArea;
    private ShowSelectDeepTimeUtil mTimeUtil;
    private DateTimeSelectBean mBean;
    private Context mContext;

    @Override
    public void execute(@NotNull Context context,int position) {
        mContext = context ;
        Activity activity = (Activity)context;
        FrameLayout mLayout = (FrameLayout)activity.findViewById(R.id.layout_frame);
        showSelectDeepUtils = new ShowSelectDeepUtils(context);
        mTimeUtil = new ShowSelectDeepTimeUtil(context);
        if (position == 0){
            showSelectDeepUtils.showDialog(new ShowSelectDeepUtils.SelectContentListener() {
                @Override
                public void onContent(String province,String city,String area) {

                    mProvince = province;
                    mCity = city;
                    mArea = area;
                    Toast.makeText(context,province + " " + city + " " + area,Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Selector data = mTimeUtil.getData();
            data.whetherShowTitle(false);
            mTimeUtil.setSelectedListener(this);
            mLayout.addView(data.getView());
        }


    }

    @Override
    public void onContent(String year,String month,String day) {
        mBean = new DateTimeSelectBean();
        mBean.setYear(year);
        mBean.setMonth(month);
        mBean.setDay(day);
        mBean.setDateTime(year+"-"+month+"-"+day);
        Toast.makeText(mContext,year+"-"+month+"-"+day,Toast.LENGTH_LONG).show();
    }
}
