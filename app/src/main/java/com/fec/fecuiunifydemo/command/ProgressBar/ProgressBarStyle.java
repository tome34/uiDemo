package com.fec.fecuiunifydemo.command.ProgressBar;

import android.content.Context;
import android.os.Handler;
import com.fec.fecuiunifydemo.ICommand;
import com.kaopiz.kprogresshud.KProgressHUD;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/12  14:11
 * @describe ${进度圈}
 */
public class ProgressBarStyle implements ICommand {

    private KProgressHUD hud;
    private Context mContext;

    @Override
    public void execute(@NotNull Context context,int position) {
        mContext = context ;
        if (position == 0){
            hud = KProgressHUD.create(mContext)
                              .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            scheduleDismiss();
        }else if (position == 1){
            hud = KProgressHUD.create(mContext).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                              .setLabel("正在加载中");
            scheduleDismiss();
        }
        hud.show();

    }

    private void scheduleDismiss() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hud.dismiss();
            }
        },2000);
    }
}
