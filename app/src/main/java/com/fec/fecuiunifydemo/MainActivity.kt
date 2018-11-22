package com.fec.fecuiunifydemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val list = MainListFactory.getMainList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val width = windowManager.defaultDisplay.width
        topbar.setTitle("UI控件")
        elv.setIndicatorBounds(width - 50, width - 10)
        val mainListAdapter = MainListAdapter(this, list)
        elv.setAdapter(mainListAdapter)
        elv.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            val child = mainListAdapter.getChild(groupPosition, childPosition) as MainListBean.ChildListBean
            val newInstance = child.command.newInstance()
            newInstance.execute(this, childPosition)
            true
        }


    }
}
