package com.fec.fecuiunifydemo.command

import android.content.Context
import android.content.Intent
import com.fec.fecuiunifydemo.ICommand
import com.fec.fecuiunifydemo.activity.NumberActivity

/**
 * Description :
 * @author  XQ Yang
 * @date 7/19/2018  10:19 AM
 */
class NumberCommand:ICommand{
    override fun execute(context: Context, position: Int) {
        when (position) {
            0-> context.startActivity(Intent(context, NumberActivity::class.java))
        }
    }
}