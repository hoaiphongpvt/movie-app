package com.example.project_android.ui.activity

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.loadinganimation.LoadingAnimation
import com.example.project_android.R
import com.example.project_android.data.services.AdsServices
import com.google.android.gms.ads.AdView
import com.example.project_android.viewmodel.VideoViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class VideoDetailsActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private lateinit var videoViewModel: VideoViewModel
    private lateinit var titlePage: TextView
    private lateinit var videoDescription: TextView
    private lateinit var mAdView: AdView
    private val adsServices = AdsServices(this)
    private lateinit var btnBack : ImageButton
    private lateinit var loadingAnim: LoadingAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_details)

        // Initialize the coroutine job
        job = Job()

        // Nhận dữ liệu từ Intent
        val videoName = intent.getStringExtra("videoName")
        val videoID = intent.getStringExtra("videoKey")
        loadingAnim = findViewById(R.id.loadingAnim)
        videoViewModel = ViewModelProvider(this).get(VideoViewModel::class.java)
        titlePage = findViewById(R.id.video_title)
        videoDescription = findViewById(R.id.video_description)
        titlePage.text = videoName
        mAdView = findViewById(R.id.adView)
        btnBack = findViewById(R.id.backButton)
        val youtubeVideo = findViewById<WebView>(R.id.mWebView)

        // Hiển thị loading animation trước khi bắt đầu coroutine
        loadingAnim.visibility = View.VISIBLE

        launch {
            val videoUrl = loadVideoAsync(videoID!!)
            youtubeVideo.loadUrl(videoUrl)

            // Ẩn loading animation sau khi hoàn thành coroutine
            loadingAnim.visibility = View.GONE
        }

        if (videoID != null) {
            videoViewModel.getVideoData(videoID) { videoDetails ->
                if (videoDetails != null) {
                    videoDescription.text = videoDetails
                }
            }
        }
        else {
            videoDescription.text = "Description not Available"
        }

        //Gọi hàm load quảng cáo
        adsServices.loadBanner(mAdView,this)

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the coroutine job when the activity is destroyed to avoid memory leaks
        job.cancel()
    }

    private suspend fun loadVideoAsync(videoID: String): String {
        return withContext(Dispatchers.Default) {
            delay(1000)
            // Trả về kết quả sau khi load URL thành công
            "https://www.youtube.com/embed/$videoID"
        }
    }
}
