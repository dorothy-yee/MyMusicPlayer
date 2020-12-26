package com.example.mymusicplayer.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusicplayer.widget.HomeItemView

/**
 * 包名： com.example.mymusicplayer.adapter
 * 类说明：
 */
class HomeAdapter:RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    class HomeHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(HomeItemView(parent?.context))
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 20
    }
}