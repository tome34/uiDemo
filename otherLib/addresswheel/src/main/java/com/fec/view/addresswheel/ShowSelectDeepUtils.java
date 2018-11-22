package com.fec.view.addresswheel;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.fec.view.addresswheel.bean.SelectAbleBean;
import com.fec.view.addresswheel.utils.BasicTool;
import com.fec.view.addresswheel.utils.IDissmissDialog;
import com.fec.view.addresswheel.widget.BottomDialog;
import com.fec.view.addresswheel.widget.DataProvider;
import com.fec.view.addresswheel.widget.ISelectAble;
import com.fec.view.addresswheel.widget.SelectedListener;
import com.fec.view.addresswheel.widget.Selector;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ShowSelectDeepUtils implements IDissmissDialog {
    private Context    mContext;
    private JSONObject mJsonObject;
    ArrayList<ISelectAble> provice = new ArrayList<>();
    ArrayList<ISelectAble> city    = new ArrayList<>();
    ArrayList<ISelectAble> area    = new ArrayList<>();
    private JSONArray mList;
    int deep = 3;
    private List<Map<String, Object>> mList5 = new ArrayList<>();
    private List<Map<String, Object>> mList6 = new ArrayList<>();
    private BottomDialog dialog;

    public static ShowSelectDeepUtils newInstance(Context mContext) {


        return new ShowSelectDeepUtils(mContext);
    }


    public ShowSelectDeepUtils(Context mContext) {
        this.mContext = mContext;
        initData();
    }


    private void initData() {
        AssetManager assets = mContext.getAssets();
        try {
            InputStream is = assets.open("address.txt");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

           // String text = new String(buffer, "GB2312");
            String text = new String(buffer, "UTF-8");
            mJsonObject = new JSONObject(text);

            JSONObject data = mJsonObject.getJSONObject("data");
            mList = data.getJSONArray("list");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //根据深度与id获取数据
    private ArrayList<ISelectAble> getDatas(int currentDeep, int id) {

        Log.d("地址选择器",""+currentDeep+","+id);
        int count = 5;
        ArrayList<ISelectAble> data1 = new ArrayList<>(count);

        try {
            List<Map<String, Object>> list4 = BasicTool.jsonArrToList(mList);
            if (currentDeep == 0) {
                provice.clear();
                for (int i = 0; i < list4.size(); i++) {
                    String sheng = String.valueOf(list4.get(i).get("name"));
                    SelectAbleBean able = new SelectAbleBean();
                    able.setName(sheng);
                    able.setId(i);
                    provice.add(able);
                }
                return provice;
            }
            if (currentDeep == 1) {
                city.clear();
                JSONArray jsonArray = (JSONArray) list4.get(id).get("chindren");//这个是市的array
                mList5.clear();
                mList5 = BasicTool.jsonArrToList(jsonArray);
                for (int i = 0; i < mList5.size(); i++) {
                    String shi = String.valueOf(mList5.get(i).get("name"));//获取市名
                    SelectAbleBean able = new SelectAbleBean();
                    able.setName(shi);
                    able.setId(i);
                    city.add(able);
                }
                return city;

            }
            if (currentDeep == 2) {
                area.clear();
                JSONArray strings = (JSONArray) mList5.get(id).get("chindren");//获取市所对应的区
                mList5.clear();
                mList5 = BasicTool.jsonArrToList(strings);
               // for (int i = 0; i < strings.length(); i++) {
                for (int i = 0; i < mList5.size(); i++) {
                    String qu = String.valueOf(mList5.get(i).get("name"));//获取市名
                    SelectAbleBean able = new SelectAbleBean();
                    able.setName(qu);
                    able.setId(i);
                    area.add(able);
                }
                return area;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provice;
    }

    public void showDialog(final SelectContentListener mSelectContentListener) {
        deep = 3;//固定了深度
        Selector selector = new Selector(mContext, deep);


        selector.setDataProvider(new DataProvider() {
            @Override
            public void provideData(int currentDeep, int preId, DataReceiver receiver) {
                //根据tab的深度和前一项选择的id，获取下一级菜单项
//                Log.i(TAG, "provideData: currentDeep >>> "+currentDeep+" preId >>> "+preId);
                receiver.send(getDatas(currentDeep, preId));
            }
        });
        selector.setSelectedListener(new SelectedListener() {
            public void onAddressSelected(ArrayList<ISelectAble> selectAbles) {
                String result = "";
                String provice = "";
                String city = "";
                String area = "";
                for (ISelectAble selectAble : selectAbles) {
                    if (selectAble != null) {
                        result += selectAble.getName() + " ";
                    }
                }
                String[] split = result.split(" ");
                if (split.length > 2) {
                    provice = split[0];
                    city = split[1];
                    area = split[2];

                }
                mSelectContentListener.onContent(provice, city, area);
//                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        selector.setDissMissDialog(ShowSelectDeepUtils.this);
        dialog = new BottomDialog(mContext);
        dialog.init(mContext, selector);
        dialog.show();
    }

    @Override
    public void dissMiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public interface SelectContentListener {
        void onContent(String provice,String city,String area);
    }

}
