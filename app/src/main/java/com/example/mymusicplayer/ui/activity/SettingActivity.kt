package com.example.mymusicplayer.ui.activity

import android.os.Build
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import com.example.mymusicplayer.R
import com.example.mymusicplayer.base.BaseActivity
import com.example.mymusicplayer.util.ToolBarManager
import org.jetbrains.anko.find

/**
 * 包名： com.example.mymusicplayer.ui.activity
 * 类说明：设置界面
 */
class SettingActivity:BaseActivity(),ToolBarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    override fun getLayoutId(): Int {
        //保存状态
        //PreferenceFragment
        return R.layout.activity_setting
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData() {
        initSettingToolBar()
        //获取推送通知有没有选中
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp.getBoolean("push",false)
    }
}