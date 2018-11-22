package com.fec.fecuiunifydemo.command;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import com.fec.view.common.dialog.TipDialogView;
import com.fec.fecuiunifydemo.ICommand;
import org.jetbrains.annotations.NotNull;

/**
 * @author tome
 * @date 2018/7/12  17:00
 * @describe ${提示框}
 */
public class PromptBox implements ICommand{

    private TipDialogView tipDialog;
    @Override
    public void execute(@NotNull Context context,int position) {
        if (position == 0){
            Toast.makeText(context,"提示语",Toast.LENGTH_SHORT).show();
        }
        /*else if (position == 1){
            tipDialog = new TipDialogView.Builder(context)
                .setIconType(TipDialogView.Builder.ICON_TYPE_SUCCESS)
                .setTipWord("发送成功")
                .create();
        }else if (position == 2){
            tipDialog = new TipDialogView.Builder(context)
                .setIconType(TipDialogView.Builder.ICON_TYPE_FAIL)
                .setTipWord("发送失败")
                .create();
        }
        if (tipDialog != null){
            //显示
            tipDialog.show();
            //1.5秒后隐藏
            scheduleDismiss();
        }*/

    }

    private void scheduleDismiss() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        },1500);
    }
}
