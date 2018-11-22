package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.ImageListActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/16  14:16
 * @describe ${图片缩放}
 */
public class PhotoViewButton implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context,ImageListActivity.class));
    }
}
