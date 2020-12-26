package com.example.mymusicplayer.adapter

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.example.mymusicplayer.model.AudioBean
import com.example.mymusicplayer.widget.MusicItemView

/**
 * 包名： com.example.mymusicplayer.adapter
 * 类说明：音乐列表界面适配器
 */
class MusicAdapter(context: Context?, c: Cursor?) : CursorAdapter(context, c) {
    //创建条目view
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return MusicItemView(context)
    }

    //view与数据的绑定
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        //view
        val itemView = view as MusicItemView
        //data
        val itemBean = cursor?.let { AudioBean.getAudioBean(it) }
        //view+data
        itemView.setData(itemBean)
    }
}