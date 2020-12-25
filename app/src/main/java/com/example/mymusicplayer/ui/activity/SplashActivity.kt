package com.example.mymusicplayer.ui.activity

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.example.mymusicplayer.R
import com.example.mymusicplayer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 包名： com.example.mymusicplayer.ui.activity
 * 类说明：欢迎界面
 */
class SplashActivity(): BaseActivity(), ViewPropertyAnimatorListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        //欢迎界面缩放，3秒钟
        ViewCompat.animate(imageView).scaleX(1.0f).scaleY(1.0f).setListener(this).duration = 3000
    }

    override fun onAnimationStart(view: View?) {

    }

    override fun onAnimationEnd(view: View?) {
        //进入主界面
        startActivityAndFinish<MainActivity>()
    }

    override fun onAnimationCancel(view: View?) {

    }
}