package com.fec.view.common.searchHistory

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fec.view.common.R
import com.fec.view.common.utils.DisplayHelper
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.fragment_search_history.*

/**
 * Description :
 * @author  XQ Yang
 * @date 7/19/2018  2:30 PM
 */


class SearchHistoryFragment : Fragment() {
    val TAG = "SearchHistoryFragment"
    lateinit var mAdapter: BaseQuickAdapter<String, BaseViewHolder>
    var onSearchTagClick: ((String) -> Unit)? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search_history, SearchHistoryHandler.historyStr) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                helper?.setText(R.id.tv, item)
                helper?.getView<ImageView>(R.id.iv_delete)?.setOnClickListener {
                    SearchHistoryHandler.historyStr.remove(item)
                    notifyItemRemoved(helper.adapterPosition)
                }
            }
        }
        rv_search.adapter = mAdapter
        mAdapter.setOnItemClickListener { item, view, position ->
            Log.i(TAG, "on item click $item")
            onSearchTagClick?.invoke(item as String)
        }
        rv_search.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        tv_clear.setOnClickListener {
            SearchHistoryHandler.clear(context)
            mAdapter.notifyDataSetChanged()
        }
    }


    fun setHotHistoryList(hotStr: List<String>) {
        for (str in hotStr) {
            layout_hot_search.addView(createNewFlexItemTextView(str))
        }
    }


    override fun onStart() {
        super.onStart()
        mAdapter.replaceData(SearchHistoryHandler.historyStr)
    }

    override fun onStop() {
        super.onStop()
        SearchHistoryHandler.save(context)
    }


    fun put(str: String) {
        if (!TextUtils.isEmpty(str)) {
            SearchHistoryHandler.put(str)
            mAdapter.notifyDataSetChanged()
        }
    }

    /**
     * 动态创建TextView
     */
    private fun createNewFlexItemTextView(str: String): TextView {
        val textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.text = str
        textView.textSize = 14f
        textView.setOnClickListener { onSearchTagClick?.invoke(str) }
        val padding = DisplayHelper.dp2px(context, 4)
        val paddingLeftAndRight = DisplayHelper.dp2px(context, 20)
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding)
        val layoutParams = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val margin = DisplayHelper.dp2px(context, 6)
        val marginTop = DisplayHelper.dp2px(context, 10)
        layoutParams.setMargins(margin, marginTop, margin, 0)
        textView.layoutParams = layoutParams
        return textView
    }
}