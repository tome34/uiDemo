package com.fec.fecuiunifydemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fec.fecuiunifydemo.R
import kotlinx.android.synthetic.main.activity_badge.*
import q.rorbin.badgeview.QBadgeView

class BadgeActivity : AppCompatActivity() {

    var b1 = 0
    var b2 = 0
    val bg1 :QBadgeView by lazy { QBadgeView(this) }
    val bg2 :QBadgeView by lazy { QBadgeView(this) }
    val bg3 :QBadgeView by lazy { QBadgeView(this) }
    val bg4 :QBadgeView by lazy { QBadgeView(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge)
        initTopBar()
        bg1.bindTarget(btn1)
        bg2.bindTarget(btn2)
       // bg3.bindTarget(tv).setBadgeTextSize(8f,true).badgeBackgroundColor = Color.BLUE
       // bg4.bindTarget(iv).setBadgeGravity(Gravity.START or Gravity.TOP).setBadgeTextColor(Color.GREEN).setBadgeNumber(b2)
        bg1.badgeNumber = 5
        bg1.setBadgePadding(1.0f, true)
        bg2.badgeNumber = -1
        btn1.setOnClickListener {
            bg1.badgeNumber = ++b1
        }
        btn2.setOnClickListener {
            bg2.badgeNumber = ++b2
        }
        tv.setOnClickListener {
            bg3.badgeText = if (b1%2==0) "haha" else "hehe"
        }
        iv.setOnClickListener {
            bg4.badgeNumber = ++b2
        }

    }

    private fun initTopBar() {
        topbar1.addLeftBackImageButton().setOnClickListener(View.OnClickListener { finish() })
        topbar1.setTitle("红点指示器")
    }
}
