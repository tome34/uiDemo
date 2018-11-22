package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.HeaderScrollViewActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/8/21  17:39
 * @describe ${头部拉伸视图效果}
 */
public class HeaderButton implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context, HeaderScrollViewActivity.class));
    }
}
