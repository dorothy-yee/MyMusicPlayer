package com.example.mymusicplayer.ui.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.example.mymusicplayer.base.BaseFragment

/**
 * 包名： com.example.mymusicplayer.ui.fragment
 * 类说明：
 */
class UserFragment:BaseFragment() {
    override fun initView(): View? {
        val tv = TextView(context)
        tv.gravity = Gravity.CENTER
        tv.setTextColor(Color.RED)
        tv.text = javaClass.simpleName
        return tv
    }
}