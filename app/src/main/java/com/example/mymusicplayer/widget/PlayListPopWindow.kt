package com.example.mymusicplayer.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.example.mymusicplayer.R
import com.example.mymusicplayer.ui.activity.MusicPlayerActivity
import org.jetbrains.anko.find

/**
 * 包名： com.example.mymusicplayer.widget
 * 类说明：
 */
class PlayListPopWindow(context:Context, adapter: BaseAdapter, listener: AdapterView.OnItemClickListener,val window: Window):PopupWindow() {
    //记录当前应用程序窗体透明度
    var alpha:Float = 0f
    init {
        //记录当前透明度
        alpha = window.attributes.alpha
        //设置布局
        val view = LayoutInflater.from(context).inflate(R.layout.pop_playlist,null,false)
        //获取listview
        val listView = view.find<ListView>(R.id.listView)
        //适配
        listView.adapter = adapter
        //设置列表条目点击事件
        listView.setOnItemClickListener(listener)
        contentView = view
        //设置宽度高度
        width = ViewGroup.LayoutParams.MATCH_PARENT
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        manager.defaultDisplay.getSize(point)
        val windowH = point.y
//        height = (windowH*3)/5
        height = windowH
        //设置获取焦点
        isFocusable = true
        //设置外部点击
        isOutsideTouchable = true
        //响应返回按钮
        setBackgroundDrawable(ColorDrawable())
        //处理动画
        animationStyle = R.style.pop
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        //popwindow显示
        val attributes = window.attributes
        attributes.alpha = 0.3f
        //设置到应用程序窗体上
        window.attributes = attributes
    }

    override fun dismiss() {
        super.dismiss()
        //popwindow隐藏 恢复应用程序窗体透明度
        val attributes =  window.attributes
        attributes.alpha = alpha
        window.attributes = attributes
    }
}