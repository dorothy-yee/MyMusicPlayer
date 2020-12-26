package com.example.mymusicplayer.widget

import android.content.Context
import android.text.format.Formatter
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.mymusicplayer.R
import com.example.mymusicplayer.model.AudioBean
import kotlinx.android.synthetic.main.item_music.view.*

/**
 * 包名： com.example.mymusicplayer.widget
 * 类说明：
 */
class MusicItemView:RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context,attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,attrs,defStyleAttr)
    init {
        View.inflate(context, R.layout.item_music,this)
    }

    //view+data
    fun setData(itemBean: AudioBean?) {
        //歌名
        title.text = itemBean!!.display_name
        //歌手
        artist.text = itemBean.artist
        //size
        size.text = Formatter.formatFileSize(context,itemBean.size)
    }
}