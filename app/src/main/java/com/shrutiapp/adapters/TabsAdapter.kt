package com.shrutiapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shrutiapp.views.fragments.FoldersFragment
import com.shrutiapp.views.fragments.PlaylistsFragment


class TabsAdapter(fm: FragmentManager, private var tabsName: List<String>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) PlaylistsFragment()
        else FoldersFragment()
    }

    override fun getCount(): Int {
        return tabsName.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabsName[position]
    }
}