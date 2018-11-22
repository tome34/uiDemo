package com.fec.fecuiunifydemo.command

import android.content.Context
import android.content.Intent
import com.fec.fecuiunifydemo.ICommand
import com.fec.fecuiunifydemo.activity.SearchActivity

/**
 * Description :
 * @author  XQ Yang
 * @date 7/19/2018  10:19 AM
 */
class SearchHistoryCommand:ICommand{
    override fun execute(context: Context, position: Int) {
        context.startActivity(Intent(context,SearchActivity::class.java))
    }
}