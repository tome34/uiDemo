package com.fec.fecuiunifydemo.command

import android.content.Context
import android.widget.Toast
import com.bigkoo.alertview.AlertView
import com.fec.fecuiunifydemo.ICommand

/**
 * Description :
 *
 * @author XQ Yang
 * @date 2018/7/12  10:40
 */
class DialogCommand : ICommand {

    override fun execute(context: Context, pos: Int) {
        //new AlertView.Builder().setMessage("message").setCancelText("取消").setTitle("标题").setItems("11111","222222").setOnItemClickListener(
        //    (view,position,data) -> Toast.makeText(context,data,Toast.LENGTH_SHORT).show()).setStyle(AlertView.Style.ActionSheet).setContext(context).build().show();

        when (pos) {
            0->{
                AlertView.showBottom(context)
                    .setTitle("请选择")
                    .setItems("拍照", "从相册中选择")

                    .onItemClick { view, position, data -> Toast.makeText(context, data, Toast.LENGTH_SHORT).show() }
                    .onDismiss { view -> Toast.makeText(context, "dismiss", Toast.LENGTH_SHORT).show() }
                    .build()
                    .show()
            }
 /*           2->{
                AlertView.showCenter(context)
                    .setItems("11111", "222222")
                    //.setTitle("标题")
                    //.setMessage("消息")
                    .onItemClick { view, position, data -> Toast.makeText(context, data, Toast.LENGTH_SHORT).show() }
                    .onDismiss { view -> Toast.makeText(context, "dismiss", Toast.LENGTH_SHORT).show() }
                    .build()
                    .show()
            }*/
            1->{
                AlertView.yesOrNo(context)
                    .setTitle("标题")
                    .setMessage("消息")
                    .onItemClick { view, position, data -> Toast.makeText(context, data, Toast.LENGTH_SHORT).show() }
                    .onDismiss { view -> Toast.makeText(context, "dismiss", Toast.LENGTH_SHORT).show() }
                    .build()
                    .show()
            }
        }
    }
}
