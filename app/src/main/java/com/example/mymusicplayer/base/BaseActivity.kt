package com.example.mymusicplayer.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 包名： com.example.mymusicplayer.base
 * 类说明：所有activity的基类
 */
abstract class BaseActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initListener()
        initData()
    }

    //初始化数据
    protected fun initData() {
        TODO("Not yet implemented")
    }

    //adapter listener
    protected fun initListener() {
        TODO("Not yet implemented")
    }

    //获取布局id
    abstract fun getLayoutId(): Int
}