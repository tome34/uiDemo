package com.fec.fecuiunifydemo

import android.content.Context

/**
 * Description :
 * @author  XQ Yang
 * @date 2018/7/12  10:00
 */

interface ICommand{
    fun execute(context: Context, position: Int=-1)
}
