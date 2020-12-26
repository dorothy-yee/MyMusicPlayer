package com.example.mymusicplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.mymusicplayer.model.AudioBean
import de.greenrobot.event.EventBus
import kotlin.random.Random

/**
 * 包名： com.example.mymusicplayer.service
 * 类说明：
 */
class MusicService:Service() {
    var mediaPlayer:MediaPlayer ?= null
    var list:ArrayList<AudioBean> ?= null
    var position :Int = 0
    val binder by lazy { MusicBinder() }
    companion object{
        val MODE_ALL = 1
        val MODE_SINGLE = 2
        val MODE_RANDOM = 3
    }
    var mode = MODE_ALL
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

    inner class MusicBinder:Binder(),Iservice, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
        fun playItem(){
        mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setOnPreparedListener(this)
                it.setOnCompletionListener(this)
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

        //跳转到当前进度进行播放
        override fun seekTo(progress: Int) {
            mediaPlayer?.seekTo(progress)
        }

        //修改播放模式
        //all->single->random
        override fun updatePlayMode() {
            when(mode){
                MODE_ALL -> mode = MODE_SINGLE
                MODE_SINGLE -> mode = MODE_RANDOM
                MODE_RANDOM -> mode = MODE_ALL
            }
        }

        //获取播放模式
        override fun getPlayMode(): Int {
            return mode
        }

        //歌曲播放完成之后回调
        override fun onCompletion(mp: MediaPlayer?) {
            //自动播放下一首
            autoPlayNext()
        }

        //根据播放模式自动播放下一首
        private fun autoPlayNext() {
            when(mode){
                MODE_ALL -> {
                    list?.let {
                        position = (position + 1) % it.size
                    }
                }
//                MODE_SINGLE ->
                MODE_RANDOM -> list?.let { position = Random.nextInt(it.size) }
            }
            playItem()
        }
    }
}