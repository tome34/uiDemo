package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.EmptyActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/12  18:30
 * @describe ${加载数据为空页面}
 */
public class EmptyLayout implements ICommand {

    private Intent mIntent;

    @Override
    public void execute(@NotNull Context context,int position) {
            mIntent = new Intent(context,EmptyActivity.class);
            mIntent.putExtra("key", 1);
            context.startActivity(mIntent);
    }
}
