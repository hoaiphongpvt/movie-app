package com.example.project_android.data.services

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class AdsServices(private val context: Context) {

    fun loadBanner(mAdView: AdView, context: Context) {
        MobileAds.initialize(context){}

        val AdRequest = AdRequest.Builder().build()
        mAdView.loadAd(AdRequest)
    }
}
