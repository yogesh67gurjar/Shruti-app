package com.shrutiapp.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.content.ContextCompat
import com.shrutiapp.adapters.TabsAdapter
import com.shrutiapp.databinding.ActivityMainBinding
import com.shrutiapp.utils.Session
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {

    @Inject
    lateinit var session: Session

    lateinit var binding: ActivityMainBinding
    lateinit var tabsAdapter: TabsAdapter
    var tabsName = mutableListOf<String>()


    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                intent = Intent(this@MainActivity, FullScreenPermission::class.java)
                startActivity(intent)
                finish()
            }
        } else if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            intent = Intent(this@MainActivity, FullScreenPermission::class.java)
            startActivity(intent)
            finish()
        }

    }

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