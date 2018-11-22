package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.SwitchButtonActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/13  13:48
 * @describe ${TODO}
 */
public class SwitchButton implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context, SwitchButtonActivity.class));
    }
}
