package com.example.mymusicplayer.ui.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusicplayer.R
import com.example.mymusicplayer.adapter.HomeAdapter
import com.example.mymusicplayer.base.BaseActivity
import com.example.mymusicplayer.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 包名： com.example.mymusicplayer.ui.fragment
 * 类说明：
 */
class HomeFragment:BaseFragment() {
    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_home,null)
    }

    override fun initListener(){
        //初始化recycleView,列表布局
        recycleView.layoutManager = LinearLayoutManager(context)
        //适配
        val adapter = HomeAdapter()
        recycleView.adapter = adapter
    }

    override fun initData(){
        //初始化数据
        loadDatas()
    }

    private fun loadDatas() {

    }

}