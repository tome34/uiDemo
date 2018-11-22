package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.RefreshLayoutActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/25  16:29
 * @describe ${下拉刷新}
 */
public class refreshButton implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context,RefreshLayoutActivity.class));
    }
}
