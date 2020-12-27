package com.example.mymusicplayer.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.mymusicplayer.model.AudioBean
import com.example.mymusicplayer.widget.PopListItemView

/**
 * 包名： com.example.mymusicplayer.adapter
 * 类说明：播放列表的适配器
 */
class PopAdapter(var list: List<AudioBean>):BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView:PopListItemView ?= null
        if (convertView == null){
            itemView = PopListItemView(parent?.context)
        }else{
            itemView = convertView as PopListItemView
        }
        itemView.setData(list.get(position))
        return itemView
    }
}