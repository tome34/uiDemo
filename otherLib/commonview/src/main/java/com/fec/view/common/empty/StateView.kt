package com.fec.view.common.empty

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.fec.core.inf.bean.IListBean
import com.fec.core.inf.bean.IStateListBean
import com.fec.core.view.IStateView
import com.fec.core.view.STATE_EMPTY
import com.fec.core.view.STATE_ERROR
import com.fec.core.view.STATE_SUCCESS
import com.fec.view.common.R
import kotlinx.android.synthetic.main.view_state_layout.view.*


/**
 *
 * @author  XQ Yang
 * @date 5/6/2018  12:23 PM
 */
class StateView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs), IStateView {
    override var btnText: String = "重新加载"
    override var errorIcon: Int = -1
    override var emptyIcon: Int = -1

    override var onRetry: (() -> Unit)? = null
    override var bindView: View? = null
    override var emptyMsg: String = "这里什么也没有噢!ヾ(･ω･`｡)"
    override var errorMsg: String = "怎么回事,出错了!(⊙_⊙)?"
    override var onStateChanged: ((Int) -> Unit)? = null
    override var curState: Int = STATE_EMPTY
    private var bindViewId = -1
    private var loadingMsg = ""


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_state_layout, this, true)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StateView)
        bindViewId = attributes.getResourceId(R.styleable.StateView_bindId, -1)
        btnText = attributes.getString(R.styleable.StateView_btnText) ?: "重新加载"
        errorMsg = attributes.getString(R.styleable.StateView_errorMsg) ?: "怎么回事,出错了!(⊙_⊙)?"
        emptyMsg = attributes.getString(R.styleable.StateView_emptyMsg) ?: "这里什么也没有噢!ヾ(･ω･`｡)"
        loadingMsg = attributes.getString(R.styleable.StateView_loadingMsg) ?: "正在加载中"
        emptyIcon = attributes.getResourceId(R.styleable.StateView_emptyIcon, -1)
        errorIcon = attributes.getResourceId(R.styleable.StateView_errorIcon, -1)
        attributes.recycle()
        empty_view_button.text = btnText
        empty_view_button.setOnClickListener {
            empty_image_view.visibility = View.GONE
            empty_view_title.visibility = View.GONE
            empty_view_button.visibility = View.GONE
            setLoadingShowing(true)
            onRetry?.invoke()
        }
        setLoadingShowing(true)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (bindViewId != -1 && bindView == null) {
            bindView = findViewById(bindViewId)
            bindView?.visibility = View.GONE
        }
    }

    override fun showSuccess() {
        hideAll()
        bindView?.visibility = View.VISIBLE
        curState = STATE_SUCCESS
        onStateChanged?.invoke(STATE_SUCCESS)
    }



    override fun showError(msg: String?) {
        hideAll()
        if (errorIcon != -1) {
            empty_image_view.visibility = View.VISIBLE
            empty_image_view.setImageResource(errorIcon)
        } else {
            empty_image_view.visibility = View.GONE
        }
        if (msg.isNullOrEmpty()) {
            empty_view_title.text = errorMsg
        } else {
            empty_view_title.text = msg
        }
        empty_view_title.visibility = View.VISIBLE
        empty_view_button.visibility = View.VISIBLE
        curState = STATE_ERROR
        onStateChanged?.invoke(STATE_ERROR)
    }

    private fun setLoadingShowing(show: Boolean) {
        if (show) {
            empty_view_loading.visibility = View.VISIBLE
            if (loadingMsg.isNotEmpty()) {
                empty_view_title.visibility = View.VISIBLE
                empty_view_title.text = loadingMsg
            } else {
                empty_view_title.visibility = View.GONE
            }
        } else {
            empty_view_title.visibility = View.GONE
            empty_view_loading.visibility = View.GONE
        }
    }

    override fun showEmpty(msg: String?) {
        hideAll()
        if (emptyIcon != -1) {
            empty_image_view.visibility = View.VISIBLE
            empty_image_view.setImageResource(emptyIcon)
        } else {
            empty_image_view.visibility = View.GONE
        }
        if (msg.isNullOrEmpty()) {
            empty_view_title.text = emptyMsg
        } else {
            empty_view_title.text = msg
        }
        empty_view_title.visibility = View.VISIBLE
        empty_view_button.visibility = View.VISIBLE
        curState = STATE_EMPTY
        onStateChanged?.invoke(STATE_EMPTY)
    }




    override fun <T, DATA : IListBean<T>> getState(bean: IStateListBean<T, DATA>?): Int {
        return if (bean?.isOk() == true) {
            if (bean.data?.list == null || bean.data!!.list!!.isEmpty()) STATE_EMPTY else STATE_SUCCESS
        } else {
            STATE_ERROR
        }
    }

    override fun <T, DATA : IListBean<T>> setData(bean: IStateListBean<T, DATA>?) {
        when (getState(bean)) {
            STATE_SUCCESS -> showSuccess()
            STATE_EMPTY -> showEmpty()
            STATE_ERROR -> showError()
        }
    }

    private fun hideAll() {
        bindView?.visibility = View.GONE
        empty_image_view.visibility = View.GONE
        empty_view_title.visibility = View.GONE
        empty_view_button.visibility = View.GONE
        setLoadingShowing(false)
    }
}