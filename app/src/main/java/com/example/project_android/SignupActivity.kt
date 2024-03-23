package com.example.project_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val webView: WebView = findViewById(R.id.web_view_signup)
        webView.webViewClient = WebViewClient()

        val url = "https://www.themoviedb.org/signup"
        webView.loadUrl(url)
    }
}