package com.fec.fecuiunifydemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fec.fecuiunifydemo.R
import com.fec.view.common.AutoLoopView
import kotlinx.android.synthetic.main.activity_loop_view.*

class LoopViewActivity : AppCompatActivity() {

    val TAG = "LoopViewActivity"
    var mAlv: AutoLoopView<String>? = null

    val list = arrayListOf<String>("http://ww1.sinaimg.cn/large/7a8aed7bjw1exvmxmy36wj20ru114gqq.jpg",
        "http://ww2.sinaimg.cn/large/c85e4a5cgw1f62hzfvzwwj20hs0qogpo.jpg",
        "http://ww3.sinaimg.cn/large/610dc034jw1f5e7x5vlfyj20dw0euaax.jpg",
        "http://ww4.sinaimg.cn/large/7a8aed7bjw1ez5zq5g685j20hj0qo0w1.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loop_view)
        initTopBar()
        mAlv = findViewById(R.id.alv)
        mAlv?.changeListener = AutoLoopView.OnChangeListener<String> { bean, position ->
            Log.d(TAG, "current item ${bean.data}")
        }
        mAlv?.let {
            it.setOnPageClickListener { bean, pos ->
//                startActivity(
//                    Intent(this@LoopViewActivity, PhotoViewActivity::class.java).putStringArrayListExtra(PhotoViewActivity.LIST, list).putExtra(PhotoViewActivity.CURRENT_POS, pos))
            }
            it.putData(this@LoopViewActivity, R.layout.item_loop_view, list, AutoLoopView.OnBindingData { view, bean ->
                if (view is ImageView) {
                  //  Glide.with(this@LoopViewActivity).load(bean).into(view)
                    var options = RequestOptions()
                    options.centerCrop()
                    options.placeholder(R.mipmap.ic_launcher)
                    Glide.with(this@LoopViewActivity).load(bean).apply(options).into(view)
                }
                view
            })
        }

    }

    private fun initTopBar() {
        topbar3.addLeftBackImageButton().setOnClickListener(View.OnClickListener { finish() })
        topbar3.setTitle("轮播图")
    }


}
