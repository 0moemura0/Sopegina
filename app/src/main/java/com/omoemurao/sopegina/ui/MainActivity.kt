package com.omoemurao.sopegina.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.omoemurao.sopegina.GifTypes
import com.omoemurao.sopegina.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var adapter: NumberAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = NumberAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = GifTypes.values()[position].urlName
        }.attach()
    }

}

class NumberAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = GifTypes.values().size

    override fun createFragment(position: Int): Fragment {
        val type = when (position) {
            GifTypes.BEST.i -> GifTypes.BEST
            GifTypes.LAST.i -> GifTypes.LAST
            GifTypes.HOT.i -> GifTypes.HOT
            GifTypes.RANDOM.i -> GifTypes.RANDOM
            else -> GifTypes.BEST
        }
        return GifFragment.newInstance(type)
    }
}