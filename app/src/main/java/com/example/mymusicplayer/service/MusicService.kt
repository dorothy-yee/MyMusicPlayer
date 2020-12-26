package com.example.mymusicplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.mymusicplayer.model.AudioBean
import de.greenrobot.event.EventBus

/**
 * 包名： com.example.mymusicplayer.service
 * 类说明：
 */
class MusicService:Service() {
    var mediaPlayer:MediaPlayer ?= null
    var list:ArrayList<AudioBean> ?= null
    var position :Int = 0
    val binder by lazy { MusicBinder() }
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //获取集合，position
        list = intent?.getParcelableArrayListExtra<AudioBean>("list")
        position = intent?.getIntExtra("position",-1)?:-1
        //开始播放音乐
        binder.playItem()
        //START_NOT_STICKY 非粘性 service杀死不会尝试重新启动
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    inner class MusicBinder:Binder(),Iservice, MediaPlayer.OnPreparedListener {
        fun playItem(){
        mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setOnPreparedListener(this)
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
            }
        }

        override fun onPrepared(mp: MediaPlayer?) {
            //播放音乐
            mediaPlayer?.start()
            //通知界面更新
            notifyUpdateUi()
        }

        //通知界面更新
        private fun notifyUpdateUi() {
            //发送端
            EventBus.getDefault().post(list?.get(position))
        }

        //更新播放状态
        override fun updatePlayState() {
            //获取当前播放状态
            var isPlaying = isPlaying()
            //切换
            isPlaying?.let{
                if (isPlaying){
                    //正播放
                    mediaPlayer?.pause()
                }else {
                    //正暂停
                    mediaPlayer?.start()
                }
            }
        }
        override fun isPlaying():Boolean?{
            return mediaPlayer?.isPlaying
        }

        //获取总进度
        override fun getDuration(): Int {
            return mediaPlayer?.duration?:0
        }

        //获取当前进度
        override fun getProgress(): Int {
            return mediaPlayer?.currentPosition?:0
        }
    }
}