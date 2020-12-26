package com.example.mymusicplayer.ui.activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import com.example.mymusicplayer.R
import com.example.mymusicplayer.base.BaseActivity
import com.example.mymusicplayer.model.AudioBean
import com.example.mymusicplayer.service.Iservice
import com.example.mymusicplayer.service.MusicService
import com.example.mymusicplayer.util.StringUtil
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_top.*

/**
 * 包名： com.example.mymusicplayer.ui.activity
 * 类说明：
 */
class MusicPlayerActivity:BaseActivity(), View.OnClickListener {
    var audioBean:AudioBean ?= null
    var duration:Int = 0
    val handler = object :Handler(){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                MSG_PROGRESS -> startUpdateProgress()
            }
        }
    }
    val MSG_PROGRESS = 0
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.state -> updatePlayState()
        }
    }

    //接收EventBus方法
    fun onEventMainThread(itemBean: AudioBean){
        //记录播放歌曲
        this.audioBean = itemBean
        //歌曲名
        audio_title.text = itemBean.display_name
        //歌手名
        artist.text = itemBean.artist
        //更新播放按钮
        updatePlayStateBtn()
        //获取总进度
        duration = iService?.getDuration()?:0
        //更新播放进度
        startUpdateProgress()
    }

    //开始更新进度
    private fun startUpdateProgress() {
        //获取当前进度
        val progress:Int = iService?.getProgress()?:0
        //更新进度
        updateProgress(progress)
        //定时获取进度,1s
        handler.sendEmptyMessageDelayed(MSG_PROGRESS,1000)
    }

    //根据当前数据进度更新界面
    private fun updateProgress(pro: Int) {
        //更新进度数值
        progress.text = StringUtil.parseDuration(pro)+"/"+StringUtil.parseDuration(duration)
    }


    //更新播放状态
    private fun updatePlayState() {
        //更新播放状态
        iService?.updatePlayState()
        //更新图标
        updatePlayStateBtn()
    }

    //根据播放状态更新图标
    private fun updatePlayStateBtn() {
        //获取当前状态
        val isPlaying = iService?.isPlaying()
        isPlaying?.let {
            //根据状态更新图标
            if (isPlaying){
                //播放图标
                state.setImageResource(R.drawable.selector_btn_audio_play)
            }else {
                //暂停图标
                state.setImageResource(R.drawable.selector_btn_audio_pause)
            }
        }
    }

    override fun initListener() {
        //播放状态切换
        state.setOnClickListener(this)
        back.setOnClickListener { finish() }
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_music_player
    }

    val conn by lazy { MusicConnection() }

    override fun initData() {
        //注册EventBus
        EventBus.getDefault().register(this)

//        val list = intent.getParcelableArrayListExtra<AudioBean>("list")
//        val position = intent.getIntExtra("position",-1)

        //通过MusicService播放音乐
        val intent = getIntent()
        //修改
        intent.setClass(this,MusicService::class.java)
        //通过intent传list，position
//        intent.putExtra("list",list)
//        intent.putExtra("position",position)
        //先开启
        startService(intent)
        //再绑定
        bindService(intent,conn, BIND_AUTO_CREATE)


        //播放音乐
//        val mediaPlayer = MediaPlayer()
//        mediaPlayer.setOnPreparedListener {
//            //开始播放
//            mediaPlayer.start()
//        }
//        mediaPlayer.setDataSource(list!!.get(position).data)
//        mediaPlayer.prepareAsync()
    }

    var iService:Iservice ?= null

    inner class MusicConnection:ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, p1: IBinder?) {
            iService = p1 as Iservice
        }

        //意外断开连接
        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //手动解绑服务
        unbindService(conn)
        //反注册EventBus
        EventBus.getDefault().unregister(this)
    }

}