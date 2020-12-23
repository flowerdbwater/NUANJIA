package com.example.nuanjia.view.home

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.nuanjia.R
import com.example.nuanjia.Tool
import kotlinx.android.synthetic.main.activity_publish.*
import kotlinx.android.synthetic.main.activity_publish.cancel
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        //设置状态栏颜色
        val tool = Tool()
        tool.setWindowStatusBarColor(this,R.color.green)
        setContentView(R.layout.activity_search)

        //点击取消，返回
        cancel.setOnClickListener {
            finish()
        }

        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        //从数据库获取数据
        getDbData()

    }

    /**
     * 获取db 数据
     */
    private fun getDbData() {

    }
}