package com.androidready.demo.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.androidready.demo.HomeFragment
import com.androidready.demo.ProfileFragment
import com.androidready.demo.SettingFragment

class MyAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                ProfileFragment()
            }
            2 -> {
                SettingFragment()
            }
            else -> HomeFragment()
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}