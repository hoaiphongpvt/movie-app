package com.example.project_android.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_android.R
import com.example.project_android.ui.fragment.DownloadsFragment
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

        //Set Fragment mặc định khi vào activity
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, HomeFragment())
                .commit()
        }

        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottomHome -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commit()
                    true // Trả về true để báo rằng sự kiện đã được xử lý
                }
                R.id.bottomSearch -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, SearchFragment()).commit()
                    true // Trả về true để báo rằng sự kiện đã được xử lý
                }
                R.id.bottomDownloads -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, DownloadsFragment()).commit()
                    true // Trả về true để báo rằng sự kiện đã được xử lý
                }
                R.id.bottomUser -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, UserFragment()).commit()
                    true // Trả về true để báo rằng sự kiện đã được xử lý
                }
                // Thêm các trường hợp cho các item khác nếu cần
                else -> false
            }
        }
    }
}