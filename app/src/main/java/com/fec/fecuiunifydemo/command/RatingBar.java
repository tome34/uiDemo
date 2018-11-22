package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.content.Intent;
import com.fec.fecuiunifydemo.ICommand;
import com.fec.fecuiunifydemo.activity.RatingBarActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/12  17:09
 * @describe ${星星控件}
 */
public class RatingBar implements ICommand{
    @Override
    public void execute(@NotNull Context context,int position) {
        context.startActivity(new Intent(context,RatingBarActivity.class));
    }
}
