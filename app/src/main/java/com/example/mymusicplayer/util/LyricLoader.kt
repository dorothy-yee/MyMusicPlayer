package com.example.mymusicplayer.util

import android.os.Environment
import java.io.File

/**
 * 包名： com.example.mymusicplayer.util
 * 类说明：歌词文件加载
 */
object LyricLoader {

    //歌词文件夹
    val dir = File(Environment.getExternalStorageDirectory(),"Download")

    //根据歌名加载歌词文件
    fun loadLyricFile(display_name:String):File{
        return File(dir,display_name+".lrc")
    }
}