package com.fec.fecuiunifydemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.fec.fecuiunifydemo.R
import kotlinx.android.synthetic.main.activity_number.*

class NumberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number)
        initTopBar()

        nm.setMaxValue(12)

        btn_get.setOnClickListener {
            Toast.makeText(this,nm.getNum().toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun initTopBar() {
        topbar2.addLeftBackImageButton().setOnClickListener { finish() }
        topbar2.setTitle("购物车加减")
    }
}
