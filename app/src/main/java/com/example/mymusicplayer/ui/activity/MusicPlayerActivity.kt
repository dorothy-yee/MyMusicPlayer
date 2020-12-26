package com.example.mymusicplayer.ui.activity

import android.media.MediaPlayer
import com.example.mymusicplayer.R
import com.example.mymusicplayer.base.BaseActivity
import com.example.mymusicplayer.model.AudioBean

/**
 * 包名： com.example.mymusicplayer.ui.activity
 * 类说明：
 */
class MusicPlayerActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_music_player
    }

    override fun initData() {
        val list = intent.getParcelableArrayListExtra<AudioBean>("list")
        val position = intent.getIntExtra("position",-1)
        //播放音乐
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener {
            //开始播放
            mediaPlayer.start()
        }
        mediaPlayer.setDataSource(list!!.get(position).data)
        mediaPlayer.prepareAsync()
    }
}