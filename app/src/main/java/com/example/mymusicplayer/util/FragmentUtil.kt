package com.example.mymusicplayer.util

import com.example.mymusicplayer.R
import com.example.mymusicplayer.base.BaseFragment
import com.example.mymusicplayer.ui.fragment.MusicFragment
import com.example.mymusicplayer.ui.fragment.HomeFragment
import com.example.mymusicplayer.ui.fragment.UserFragment
import com.example.mymusicplayer.ui.fragment.VbangFragment

/**
 * 包名： com.example.mymusicplayer.util
 * 类说明：管理fragment的util类
 */
class FragmentUtil private constructor(){//私有化构造方法

    val homeFragment by lazy { HomeFragment() }
    val vbangFragment by lazy { VbangFragment() }
    val musicFragment by lazy { MusicFragment() }
    val userFragment by lazy { UserFragment() }

    companion object {
        val fragmentUtil by lazy { FragmentUtil() }
    }

    //根据tabid获取对应的fragment
    fun getFragment(tabId:Int):BaseFragment?{
        when (tabId){
            R.id.tab_home -> return homeFragment
            R.id.tab_vbang -> return vbangFragment
            R.id.tab_music -> return musicFragment
            R.id.tab_user -> return userFragment
        }
        return null
    }
}