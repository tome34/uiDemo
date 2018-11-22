package com.fec.fecuiunifydemo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fec.fecuiunifydemo.R;
import com.fec.fecuiunifydemo.bean.PoiBean;
import java.util.List;

/**
 * @author tome
 * @date 2018/8/17  15:58
 * @describe ${地址列表}
 */
public class PoiAdapter extends BaseQuickAdapter<PoiBean, BaseViewHolder>{

    private Context mContext ;
    private List<PoiBean> mData;

    public PoiAdapter(Context context, @Nullable List<PoiBean> data) {
        super(R.layout.item_select_address,data);
        mContext = context ;
        mData = data ;
    }

    @Override
    protected void convert(BaseViewHolder helper,PoiBean item) {
        if (item != null){
            helper.setText(R.id.tv_title , item.getTitleName());
            helper.setText(R.id.tv_address , item.getCityName()+item.getAd()+item.getSnippet());


        }
    }
}
