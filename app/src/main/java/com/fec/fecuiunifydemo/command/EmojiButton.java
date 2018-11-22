package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.EmojiActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/8/28  14:46
 * @describe ${表情键盘}
 */
public class EmojiButton implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context, EmojiActivity.class));
    }
}
