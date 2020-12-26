package com.example.mymusicplayer.service

/**
 * 包名： com.example.mymusicplayer.service
 * 类说明：
 */
interface Iservice {
    fun updatePlayState()
    fun isPlaying():Boolean?
    fun getDuration(): Int
    fun getProgress(): Int
    fun seekTo(progress: Int)
    fun updatePlayMode()
    fun getPlayMode(): Int
}