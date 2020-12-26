package com.example.mymusicplayer.ui.activity

import android.os.Build
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import com.example.mymusicplayer.R
import com.example.mymusicplayer.base.BaseActivity
import com.example.mymusicplayer.util.FragmentUtil
import com.example.mymusicplayer.util.ToolBarManager
import kotlinx.android.synthetic.main.activity_main.*
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

    override fun initListener() {
        //设置tab切换监听
        bottomBar.setOnTabReselectListener{
             val transaction = supportFragmentManager.beginTransaction()
            //it代表tabid
            FragmentUtil.fragmentUtil.getFragment(it)?.let { it1 ->
                transaction.replace(R.id.container,
                    it1,it.toString())
            }
            transaction.commit()
        }
    }

}

