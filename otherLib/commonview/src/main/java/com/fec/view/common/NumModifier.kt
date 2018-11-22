package com.fec.view.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast


@SuppressLint("ShowToast")
/**
 * 数量加减器
 * @author XQ Yang
 * @date 2017/11/16  16:57
 */
class NumModifier @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private var btnSub: ImageView? = null
    private var et: EditText? = null
    private var btnAdd: ImageView? = null

    private var num = 1
    private var max = Int.MAX_VALUE

    var onModifyListener: ((Int) -> Unit)? = null

    var modifyCallback: ((Int, (Int) -> Unit) -> Unit)? = null
    private fun findViews() {
        btnSub = findViewById(R.id.btn_sub)
        et = findViewById(R.id.et)
        btnAdd = findViewById(R.id.btn_add)
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        //重写此方法,丢弃自动保存的数据
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_num_modifier, this, true)
        findViews()
        btnSub!!.setOnClickListener { v ->
            setNum(num - 1, true)
        }
        btnAdd!!.setOnClickListener { v ->
            setNum(num + 1, true)
        }
        et!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(s)) {
                    num = 1
                } else {
                    val int = Integer.parseInt(s.toString())
                    num = if (int < max) int else if (max == 0) 1 else max
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (modifyCallback != null && s.isNotEmpty()) {
                    val temp = Integer.parseInt(s.toString())
                    modifyCallback!!.invoke(temp) { i ->
                        if (i != temp) {
                            s.replace(0, s.length, i.toString())
                        }
                    }
                }
            }
        })

        et?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                setNum(et?.text?.toString()?.toInt() ?: num, true)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private val NumModifier.toast: Toast by lazy { Toast.makeText(context, "超出允许的数量限制", Toast.LENGTH_SHORT) }

    fun setNum(num: Int, needNotify: Boolean) {
        var n = num
        if (n < 1) {
            n = 1
        } else if (n > max) {
            n = if (max == 0) 1 else {
                toast.show()
                max
            }
        }
        this.num = n
        val text = this.num.toString()
        et?.setText(text)
        et?.setSelection(text.length)
        if (needNotify) {
            onModifyListener?.invoke(this.num)
        }
    }


    fun getNum(): Int {
        return if (num > max) max else num
    }

    fun setMaxValue(max: Int) {
        this.max = max
        if (max == 0) {
            setNum(1, false)
        }
    }
}
