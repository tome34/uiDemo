package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.ButtonStyleActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/8/6  15:12
 * @describe ${TODO}
 */
public class ButtonStyle implements ICommand {
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context,ButtonStyleActivity.class));
    }
}
