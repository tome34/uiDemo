package com.fec.fecuiunifydemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fec.fecuiunifydemo.R
import kotlinx.android.synthetic.main.activity_textarea.*

class TextAreaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textarea)

        initTopBar()

        tv1.setOnClickListener {
            tv1.text = ta.text
        }

    }

    private fun initTopBar() {
        topbar.addLeftBackImageButton().setOnClickListener(View.OnClickListener { finish() })
        topbar.setTitle("留言框")
    }
}
