package com.example.mymusicplayer.widget

import android.content.Context
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
class PopListItemView:RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context,attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,attrs,defStyleAttr)
    init {
        View.inflate(context,R.layout.item_pop,this)
    }

    fun setData(data: AudioBean) {
        //title
        title.text = data.display_name
        //artist
        artist.text = data.artist
    }
}