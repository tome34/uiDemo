package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.AmapAddressActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/8/17  14:47
 * @describe ${高德地图地址选择器}
 */
public class AmapButton implements ICommand {
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context, AmapAddressActivity.class));
    }
}
