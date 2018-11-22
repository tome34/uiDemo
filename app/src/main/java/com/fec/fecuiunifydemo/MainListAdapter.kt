package com.fec.fecuiunifydemo

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

/**
 * Description :
 * @author  XQ Yang
 * @date 2018/7/12  9:53
 */
class MainListAdapter(val mContext:Context,val commandList: List<MainListBean>) : BaseExpandableListAdapter() {


    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        return if (convertView != null && convertView is TextView) {
            convertView.text = commandList[groupPosition].title
            convertView
        } else {
            val textView = LayoutInflater.from(mContext).inflate(R.layout.main_list_item,parent,false) as TextView
            textView.text = commandList[groupPosition].title
            textView.setBackgroundColor(Color.WHITE)
            textView
        }
    }

    override fun getChildrenCount(groupPosition: Int): Int {
       return commandList[groupPosition].childList.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
       return commandList[groupPosition].childList[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
       return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        return if (convertView != null && convertView is TextView) {
            convertView.text = commandList[groupPosition].childList[childPosition].title
            convertView
        } else {
            val textView  = LayoutInflater.from(mContext).inflate(R.layout.main_list_item,parent,false) as TextView
            textView.text = commandList[groupPosition].childList[childPosition].title
            textView.setBackgroundColor(Color.parseColor("#eeeeee"))
            textView
        }
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
       return commandList.size
    }

    override fun getGroup(groupPosition: Int): Any {
       return commandList[groupPosition]
    }

}