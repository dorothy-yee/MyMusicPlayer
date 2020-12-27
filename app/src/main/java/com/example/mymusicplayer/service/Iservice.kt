package com.example.mymusicplayer.service

import com.example.mymusicplayer.model.AudioBean

/**
 * 包名： com.example.mymusicplayer.service
 * 类说明：
 */
interface Iservice {
    fun updatePlayState()
    fun isPlaying(): Boolean?
    fun getDuration(): Int
    fun getProgress(): Int
    fun seekTo(progress: Int)
    fun updatePlayMode()
    fun getPlayMode(): Int
    fun playPre()
    fun playNext()
    fun getPlayList(): List<AudioBean>?
    fun playPostion(position: Int)
}