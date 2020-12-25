package com.example.mymusicplayer.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 包名： com.example.mymusicplayer.base
 * 类说明：所有activity的基类
 */
abstract class BaseActivity:AppCompatActivity(),AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initListener()
        initData()
    }

    //初始化数据
    protected open fun initData() {

    }

    //adapter listener
    protected open fun initListener() {

    }

    //获取布局id
    abstract fun getLayoutId(): Int

    protected  fun myToast(msg:String){
        runOnUiThread { toast(msg) }
    }

    //start一个activity并finish当前页面
    inline fun <reified T:BaseActivity> startActivityAndFinish(){
        startActivity<T>()
        finish()
    }
}