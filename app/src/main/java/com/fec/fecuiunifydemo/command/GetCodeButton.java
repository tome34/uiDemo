package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.MsgCodeActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/31  10:04
 * @describe ${验证码倒计时}
 */
public class GetCodeButton implements ICommand {

    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context,MsgCodeActivity.class));
    }
}
