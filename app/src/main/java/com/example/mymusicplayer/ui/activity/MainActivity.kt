package com.example.mymusicplayer.ui.activity

import android.os.Build
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import com.example.mymusicplayer.R
import com.example.mymusicplayer.base.BaseActivity
import com.example.mymusicplayer.util.ToolBarManager
import org.jetbrains.anko.find

class MainActivity : BaseActivity(),ToolBarManager {

    //惰性加载（用的时候才初始化）
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData() {
        initMainToolBar()
    }

}

