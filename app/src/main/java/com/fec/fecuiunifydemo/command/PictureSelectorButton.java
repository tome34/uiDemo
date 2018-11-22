package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.PictureSelectorActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/16  15:30
 * @describe ${图片选择器}
 */
public class PictureSelectorButton implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context,PictureSelectorActivity.class));
    }
}
