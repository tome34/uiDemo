package com.fec.view.addresswheel;

import android.content.Context;
import android.util.Log;
import com.fec.view.addresswheel.bean.SelectAbleBean;
import com.fec.view.addresswheel.widget.DataProvider;
import com.fec.view.addresswheel.widget.ISelectAble;
import com.fec.view.addresswheel.widget.SelectedListener;
import com.fec.view.addresswheel.widget.Selector;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ShowSelectDeepTimeUtil {


    private final Context mContext;
    private List<ISelectAble> year;
    private List<ISelectAble> month;
    private List<ISelectAble> day;
    int deep = 3;//深度

    private int selectYear;

    public static final int MAX_YEAR = 2100;
    public static final int MIN_YEAR = 2005;
    private SelectContentListener listener;


    public ShowSelectDeepTimeUtil(Context mContext) {
        this.mContext = mContext;
    }

    public Selector getData(){
        final Selector selector = new Selector(mContext,deep);
        selector.setDataProvider(new DataProvider() {
            @Override
            public void provideData(int currentDeep, int preId, DataReceiver receiver) {
                Log.d("","provideData:currentDeep:"+currentDeep+",preId:"+preId);
                if (currentDeep==1){
                    //年份
                    selectYear = preId;
                }
                receiver.send(getNextData(currentDeep,preId));
                if (currentDeep==0){
                    Calendar instance = Calendar.getInstance();
                    int year = instance.get(Calendar.YEAR);
                    selector.onItemClick(null,null,year-MIN_YEAR,year-MIN_YEAR);
                }
            }
        });
        selector.setSelectedListener(new SelectedListener() {
            @Override
            public void onAddressSelected(ArrayList<ISelectAble> selectAbles) {
                String result = "";
                String day = "";
                String year = "";
                String month = "";
                for (ISelectAble selectAble : selectAbles) {
                    if (selectAble != null) {
                        result += selectAble.getName() + " ";
                    }
                }
                String[] split = result.split(" ");
                if (split.length > 2) {
                    year = split[0];
                    month = split[1];
                    day = split[2];

                }
                if (listener!=null){
                    listener.onContent(year, month, day);
                }
            }
        });
        return selector;
    }


    public List<ISelectAble> getNextData(int currentDeep,int reId){
        try {
            if (currentDeep==0) {
                //获取年份
                if (year==null){
                    year = new ArrayList<>();
                }else{
                    year.clear();
                }
                for (int i = MIN_YEAR; i < MAX_YEAR; i++) {
                    SelectAbleBean bean = new SelectAbleBean();
                    bean.setName(i + "");
                    bean.setId(i);
                    year.add(bean);
                }
                return year;
            }else if(currentDeep==1){
                if (month==null){
                    month = new ArrayList<>();
                }else{
                    month.clear();
                }
                for (int i = 0; i < 12; i++) {
                    SelectAbleBean bean = new SelectAbleBean();
                    bean.setName(((i+1)+"").length()==1?"0"+(i+1):(i+1)+"");
                    bean.setId(i+1);
                    month.add(bean);
                }
                return month;
            }else if(currentDeep==2){
                if (day==null){
                    day = new ArrayList<>();
                }
                day.clear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(selectYear,reId,0);
                int days = calendar.get(Calendar.DAY_OF_MONTH);
                for (int i = 0; i < days; i++) {
                    SelectAbleBean bean = new SelectAbleBean();
                    bean.setName(((i+1)+"").length()==1?"0"+(i+1):(i+1)+"");
                    bean.setId(i);
                    day.add(bean);
                }
                return day;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return year;
    }


    public void setSelectedListener(SelectContentListener listener){
        this.listener = listener;
    }


    public interface SelectContentListener {
        void onContent(String year,String month,String day);
    }

}
