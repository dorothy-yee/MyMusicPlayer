package com.example.mymusicplayer.util

import android.content.Intent
import android.os.Build
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import com.example.mymusicplayer.R
import com.example.mymusicplayer.ui.activity.SettingActivity

/**
 * 包名： com.example.mymusicplayer.util
 * 类说明：所有toolbar的管理类
 */
interface ToolBarManager {

    val toolbar: Toolbar

    //初始化主界面的toolbar
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun initMainToolBar(){
        toolbar.title = "MyMusicPlayer"
        toolbar.inflateMenu(R.menu.main)
        //kotlin和java调用特性
        //如果java接口中只有一个未实现的方法，可以省略对象直接用{}表示未实现的方法
//        toolbar.setOnMenuItemClickListener {
//            toolbar.context.startActivity(Intent( toolbar.context,SettingActivity::class.java))
//            true
//        }
        toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.setting -> {
                        //跳转到设置界面
                        toolbar.context.startActivity(Intent( toolbar.context,SettingActivity::class.java))
                    }
                }
                return true
            }

        })
    }

    //处理设置界面的toolbar
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun initSettingToolBar(){
        toolbar.title = "设置"
    }
}

