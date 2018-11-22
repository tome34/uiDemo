/*
 * Copyright (c) 18-4-3 下午3:45. XQ Yang
 */

package com.fec.view.common.searchHistory

import android.content.Context
import java.util.*

/**
 *
 * @author  XQ Yang
 * @date 2018/4/3  15:45
 */
object SearchHistoryHandler {
    var MAX_SAVE_SEARCH_HISTORY_COUNT = 12
    var userAccount:String = ""
    var SEARCH_HISTORY_KEY = userAccount + "_search_history_key"

    var historyStr: MutableList<String> = mutableListOf()
        get() {
            if (!inited) {
                throw IllegalStateException("no inited")
            }
            return field
        }
        private set(value) {
            field = value
        }

    private var inited = false


    fun init(context: Context,userAccount:String) {
        if (!inited) {
            this.userAccount = userAccount
            //todo 临时关闭,待完成后依赖私服上的fecCommon包后打开
//            if (SPUtil.getFecSP(context).contains(SEARCH_HISTORY_KEY)) {
//                historyStr = SPUtil.getObject(context, SEARCH_HISTORY_KEY) as MutableList<String>
//            }
            inited = true
        }
    }

    fun save(context: Context?) {
        if (historyStr.isNotEmpty()) {
            val set = LinkedHashSet<String>()
            var newList: MutableList<String> = ArrayList()
            set.addAll(historyStr)
            newList.addAll(set)
            if (newList.size > MAX_SAVE_SEARCH_HISTORY_COUNT) {
                newList = newList.subList(0, MAX_SAVE_SEARCH_HISTORY_COUNT)
            }
//            SPUtil.setObject(context, SEARCH_HISTORY_KEY, newList)
        }
    }

    fun clear(context: Context?) {
        historyStr.clear()
//        SPUtil.removeObject(context, SEARCH_HISTORY_KEY)
    }

    fun put(str: String) {
        if (historyStr.contains(str)) {
            historyStr.removeAll { it == str }
        }
        historyStr.add(0, str)
    }

}