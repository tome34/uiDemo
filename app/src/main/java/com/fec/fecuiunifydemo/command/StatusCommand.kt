package com.fec.fecuiunifydemo.command

import android.content.Context
import android.content.Intent
import com.fec.fecuiunifydemo.ICommand
import com.fec.fecuiunifydemo.activity.ImgTranslucentActivity
import com.fec.fecuiunifydemo.activity.TranslucentActivity

/**
 * Description :
 * @author  XQ Yang
 * @date 7/19/2018  10:19 AM
 */
class StatusCommand:ICommand{
    override fun execute(context: Context, position: Int) {
        when (position) {
            0-> context.startActivity(Intent(context, TranslucentActivity::class.java))
            1-> context.startActivity(Intent(context, ImgTranslucentActivity::class.java))
        }
    }
}