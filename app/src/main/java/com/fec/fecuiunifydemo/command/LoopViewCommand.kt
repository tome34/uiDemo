package com.fec.fecuiunifydemo.command

import android.content.Context
import android.content.Intent
import com.fec.fecuiunifydemo.ICommand
import com.fec.fecuiunifydemo.activity.LoopViewActivity

/**
 * Description :
 * @author  XQ Yang
 * @date 7/19/2018  10:19 AM
 */
class LoopViewCommand:ICommand{
    override fun execute(context: Context, position: Int) {
      context.startActivity(Intent(context, LoopViewActivity::class.java))
    }
}