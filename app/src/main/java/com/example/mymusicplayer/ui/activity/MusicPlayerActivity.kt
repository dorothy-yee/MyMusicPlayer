package com.example.mymusicplayer.ui.activity

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.example.mymusicplayer.R
import com.example.mymusicplayer.adapter.PopAdapter
import com.example.mymusicplayer.base.BaseActivity
import com.example.mymusicplayer.model.AudioBean
import com.example.mymusicplayer.service.Iservice
import com.example.mymusicplayer.service.MusicService
import com.example.mymusicplayer.util.StringUtil
import com.example.mymusicplayer.widget.PlayListPopWindow
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_top.*

/**
 * 包名： com.example.mymusicplayer.ui.activity
 * 类说明：
 */
class MusicPlayerActivity:BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener,
    AdapterView.OnItemClickListener {

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
            R.id.mode -> updatePlayMode()
            R.id.pre -> iService?.playPre()
            R.id.next -> iService?.playNext()
            R.id.playlist -> showPlayList()
        }
    }


    //显示播放列表
    private fun showPlayList() {
        window
        val list = iService?.getPlayList()
        list?.let {
            //创建adapter
            val adapter = PopAdapter(list)
            //获取底部高度
            val bottomH = audio_player_bottom.height
            val popWindow = PlayListPopWindow(this, adapter,this,window)
            popWindow.showAsDropDown(audio_player_bottom,0,bottomH)
        }
    }

    //更新播放模式
    private fun updatePlayMode() {
        //修改service中mode
        iService?.updatePlayMode()
        //修改界面模式图标
        updatePlayModeBtn()
    }

    //根据播放模式切换图标
    private fun updatePlayModeBtn() {
        iService?.let {
            //获取播放模式
            val modeI:Int =  it.getPlayMode()
            //设置图标
            when(modeI){
                MusicService.MODE_ALL -> mode.setImageResource(R.drawable.selector_btn_playmode_order)
                MusicService.MODE_SINGLE -> mode.setImageResource(R.drawable.selector_btn_playmode_single)
                MusicService.MODE_RANDOM -> mode.setImageResource(R.drawable.selector_btn_playmode_random)
            }
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
        //获取进度条最大值
        progress_sk.max = duration
        //更新播放进度
        startUpdateProgress()
        //更新播放模式图标
        updatePlayModeBtn()
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
        //更新进度条
        progress_sk.setProgress(pro)
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
                //开启进度更新
                handler.sendEmptyMessage(MSG_PROGRESS)
            }else {
                //暂停图标
                state.setImageResource(R.drawable.selector_btn_audio_pause)
                //停止更新进度
                handler.removeMessages(MSG_PROGRESS)
            }
        }
    }

    override fun initListener() {
        //播放状态切换
        state.setOnClickListener(this)
        back.setOnClickListener { finish() }
        //进度条变化监听
        progress_sk.setOnSeekBarChangeListener(this)
        //播放模式点击事件
        mode.setOnClickListener(this)
        //上一首
        pre.setOnClickListener(this)
        //下一首
        next.setOnClickListener(this)
        //播放列表
        playlist.setOnClickListener(this)
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

        //先绑定
        bindService(intent,conn, BIND_AUTO_CREATE)
        //再开启
        startService(intent)


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
        //清空handler发送的所有消息，防止内存泄漏
        handler.removeCallbacksAndMessages(null)
    }

    //进度改变回调
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        //判断是否为用户操作
        if (!fromUser) return
        //更新播放进度
        iService?.seekTo(progress)
        //更新界面进度显示
        updateProgress(progress)
    }

    //手指触摸seekbar回调
    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    //手指离开seekbar回调
    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    //弹出的播放列表条目点击事件
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //播放当前歌曲
        iService?.playPostion(position)
    }

}