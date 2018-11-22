package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.ZxingActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/31  13:51
 * @describe ${TODO}
 */
public class ZxingButton implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context, ZxingActivity.class));
    }
}
