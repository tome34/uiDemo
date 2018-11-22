package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.PopupAutoActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/19  10:05
 * @describe ${自适应弹框}
 */
public class PopupView implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context,PopupAutoActivity.class));
    }
}
