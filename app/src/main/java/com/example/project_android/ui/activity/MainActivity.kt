package com.example.project_android.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_android.R
import com.example.project_android.ui.fragment.HomeFragment
import com.example.project_android.ui.fragment.SearchFragment
import com.example.project_android.ui.fragment.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.bottomNav)

        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            homeFragment.arguments = intent.extras
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, homeFragment).commit()
        }

        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.bottomSearch -> SearchFragment()
                R.id.bottomUser -> UserFragment()
                else -> HomeFragment()
            }
            fragment.arguments = intent.extras
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit()
            true
        }
    }
}