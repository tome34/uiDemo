package com.fec.fecuiunifydemo.command

import android.content.Context
import android.content.Intent
import com.fec.fecuiunifydemo.ICommand
import com.fec.fecuiunifydemo.fragment.QDTabSegmentFixModeActivity
import com.fec.fecuiunifydemo.fragment.QDTabSegmentScrollableModeActivity

/**
 * Description :
 *
 * @author XQ Yang
 * @date 2018/7/12  10:40
 */
class TabCommand : ICommand {

    override fun execute(context: Context, pos: Int) {
        //new AlertView.Builder().setMessage("message").setCancelText("取消").setTitle("标题").setItems("11111","222222").setOnItemClickListener(
        //    (view,position,data) -> Toast.makeText(context,data,Toast.LENGTH_SHORT).show()).setStyle(AlertView.Style.ActionSheet).setContext(context).build().show();

        when (pos) {
            0->{
                context.startActivity(Intent(context,QDTabSegmentFixModeActivity::class.java))
            }
            1->{
                context.startActivity(Intent(context,QDTabSegmentScrollableModeActivity::class.java))
            }
        }



    }
}
