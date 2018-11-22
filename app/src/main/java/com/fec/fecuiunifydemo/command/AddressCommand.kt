package com.fec.fecuiunifydemo.command

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.bigkoo.pickerview.R
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.utils.FecAddressView
import com.bigkoo.pickerview.view.TimePickerView
import com.fec.fecuiunifydemo.ICommand
import java.text.SimpleDateFormat
import java.util.*

/**
 * Description :
 * @author  XQ Yang
 * @date 7/17/2018  11:50 AM
 */
class AddressCommand:ICommand{
    var mAddressViewUtils: FecAddressView? = null
    var pvTime: TimePickerView? = null
    var pvTime1: TimePickerView? = null
    var pvTime2: TimePickerView? = null



    override fun execute(context: Context, position: Int) {
        when (position) {
            0->{
                if (mAddressViewUtils == null) {
                    mAddressViewUtils = FecAddressView.getInstance(context).setSelected("广东", "深圳市", "龙华新区").setSelectListener { province, city, area -> Toast.makeText(context, province.name + city.name + area.name, Toast.LENGTH_SHORT).show() }.build()
                }
                mAddressViewUtils?.show()
            }
            1->{
                if (pvTime1 == null) {
                    initTimePicker1(context)
                }
                pvTime1?.show()
            }
            2->{
                if (pvTime2 == null) {
                   initTimePicker2(context)
                }
                pvTime2?.show()
            }
            3->{
                if (pvTime == null) {
                   initTimePicker(context)
                }
                pvTime?.show()
            }
        }
    }


    private fun initTimePicker(context: Context) {//Dialog 模式下，在底部弹出
        val startTime = Calendar.getInstance()
        startTime.set(Calendar.YEAR, 1992)
        startTime.set(Calendar.MONTH, 0)
        startTime.set(Calendar.DAY_OF_MONTH, 30)
        startTime.set(Calendar.HOUR_OF_DAY, 0)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.SECOND, 10)
        val endTime = Calendar.getInstance()
        endTime.time = Date()

        pvTime = TimePickerBuilder(context, OnTimeSelectListener { date, v ->
            Toast.makeText(context, getTime(date), Toast.LENGTH_SHORT).show()
            Log.i("pvTime", "onTimeSelect")
        })
            .setTimeSelectChangeListener { Log.i("pvTime", "onTimeSelectChanged") }
            .setType(booleanArrayOf(true, true, true, true, true, true))
            .setSubmitColor(context.getResources().getColor(R.color.alert_button_destructive))//确定按钮文字颜色
            .setCancelColor(context.getResources().getColor(R.color.alert_button_cancel))//取消按钮文字颜色
            .isDialog(true)
            .setRangDate(startTime, endTime)
            .build()
        pvTime?.setBottom()
    }
    private fun initTimePicker1(context: Context) {//Dialog 模式下，在底部弹出
        val startTime = Calendar.getInstance()
        startTime.set(Calendar.YEAR, 1992)
        startTime.set(Calendar.MONTH,1)
        startTime.set(Calendar.DAY_OF_MONTH, 25)
        startTime.set(Calendar.HOUR_OF_DAY, 4)
        startTime.set(Calendar.MINUTE, 5)
        startTime.set(Calendar.SECOND, 10)
        val endTime = Calendar.getInstance()
        endTime.time = Date()

        pvTime1 = TimePickerBuilder(context, OnTimeSelectListener { date, v ->
            Toast.makeText(context, getTime(date), Toast.LENGTH_SHORT).show()
            Log.i("pvTime", "onTimeSelect")
        })
            .setTimeSelectChangeListener { Log.i("pvTime", "onTimeSelectChanged") }
            .setType(booleanArrayOf(false, false, false, true, true, true))
            .isDialog(true)
            .setTitleText("时分秒")
            .setSubmitColor(context.getResources().getColor(R.color.alert_button_destructive))//确定按钮文字颜色
            .setCancelColor(context.getResources().getColor(R.color.alert_button_cancel))//取消按钮文字颜色
            .setRangDate(startTime, endTime)
            .build()
        pvTime1?.setBottom()
    }

    private fun initTimePicker2(context: Context) {//Dialog 模式下，在底部弹出
        val startTime = Calendar.getInstance()
        startTime.set(Calendar.YEAR, 1992)
        startTime.set(Calendar.MONTH,1)
        startTime.set(Calendar.DAY_OF_MONTH, 25)
        startTime.set(Calendar.HOUR_OF_DAY, 0)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.SECOND, 0)
        val endTime = Calendar.getInstance()
        endTime.time = Date()

        pvTime2 = TimePickerBuilder(context, OnTimeSelectListener { date, v ->
            Toast.makeText(context, getTime(date), Toast.LENGTH_SHORT).show()
            Log.i("pvTime", "onTimeSelect")
        })
            .setTimeSelectChangeListener { Log.i("pvTime", "onTimeSelectChanged") }
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .isDialog(true)
            .setTitleText("年月日")
            .setSubmitColor(context.getResources().getColor(R.color.alert_button_destructive))//确定按钮文字颜色
            .setCancelColor(context.getResources().getColor(R.color.alert_button_cancel))//取消按钮文字颜色
            .setRangDate(startTime, endTime)
            .build()
        pvTime2?.setBottom()
    }

    private fun getTime(date: Date): String {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.time)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)
    }

}