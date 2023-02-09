package com.example.leafapp.ui.home.homefragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabLayoutAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val fragmentsList: MutableList<Fragment> = ArrayList()
    private val fragmentsTitleList: MutableList<String> = ArrayList()


    fun addFragment(fragment: Fragment, title: String) {
        fragmentsList.add(fragment)
        fragmentsTitleList.add(title)
    } //end AddFragment


    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    } //end getItem


    override fun getCount(): Int {
        return fragmentsList.size
    } //end get count


    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentsTitleList[position]
    }
}