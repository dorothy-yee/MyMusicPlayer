package com.example.mymusicplayer.ui.fragment

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mymusicplayer.R
import org.jetbrains.anko.toast

/**
 * 包名： com.example.mymusicplayer.ui.fragment
 * 类说明：
 */
class SettingFragment:PreferenceFragment() {
    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addPreferencesFromResource(R.xml.setting)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onPreferenceTreeClick(
        preferenceScreen: PreferenceScreen?,
        preference: Preference?
    ): Boolean {
        val key = preference?.key
        if ("newest".equals(key)){
            //版本
           toast( "已更新到最新版本")

        }
        return super.onPreferenceTreeClick(preferenceScreen, preference)
    }
}