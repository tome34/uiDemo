package com.fec.fecuiunifydemo.command

import android.content.Context
import android.content.Intent
import com.fec.fecuiunifydemo.ICommand
import com.fec.fecuiunifydemo.activity.BadgeActivity

/**
 * Description :
 * @author  XQ Yang
 * @date 7/19/2018  10:19 AM
 */
class BadgeCommand:ICommand{
    override fun execute(context: Context, position: Int) {
        context.startActivity(Intent(context,BadgeActivity::class.java))
    }
}