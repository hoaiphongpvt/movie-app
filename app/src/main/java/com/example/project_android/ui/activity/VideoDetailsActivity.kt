package com.example.project_android.ui.activity

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project_android.R

class VideoDetailsActivity : AppCompatActivity() {

    private lateinit var titlePage: TextView
    private lateinit var btnBack: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_details)

        // Nhận dữ liệu từ Intent
        val videoName = intent.getStringExtra("videoName")
        val videoKey = intent.getStringExtra("videoKey")
        titlePage = findViewById(R.id.video_title)
        btnBack = findViewById(R.id.backButton)
        titlePage.text = videoName
        val frameVideo =
            "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoKey\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
        val displayYoutubeVideo = findViewById<View>(R.id.mWebView) as WebView
        displayYoutubeVideo.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
        val webSettings = displayYoutubeVideo.settings
        webSettings.javaScriptEnabled = true
        displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8")

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}
