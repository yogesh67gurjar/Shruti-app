package com.shrutiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shrutiapp.adapters.TabsAdapter
import com.shrutiapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var tabsAdapter: TabsAdapter
    var tabsName = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initsetup()
    }

    private fun initsetup() {
        tabsName.add("Playlists")
        tabsName.add("Folders")

        tabsAdapter = TabsAdapter(getSupportFragmentManager(), tabsName)
        binding.viewPager.adapter = tabsAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}