package com.fec.fecuiunifydemo.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fec.fecuiunifydemo.R;
import java.util.List;

/**
 * @Created by TOME .
 * @描述 ${TODO}
 */

public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item != null){
            ImageView ivPhoto = (ImageView) helper.getView(R.id.iv_photo);
            String url = item;
            RequestOptions options = new RequestOptions()
               // .centerCrop()
                .placeholder(R.mipmap.ic_image_loading);
            Glide.with(mContext).load(url).apply(options).into(ivPhoto);

        }

    }

}
