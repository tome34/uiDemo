package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.MarqueeStyleActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/13  17:59
 * @describe ${TODO}
 */
public class MarqueeViewButton implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context,MarqueeStyleActivity.class));
    }
}
