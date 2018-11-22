package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.CustomdownTimeActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/13  17:29
 * @describe ${TODO}
 */
public  class CountdownButton implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context,CustomdownTimeActivity.class));
    }
}
