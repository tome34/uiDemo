package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.TextAreaActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/20  9:46
 * @describe ${留言框}
 */
public class MessageBox implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context,TextAreaActivity.class));
    }
}
