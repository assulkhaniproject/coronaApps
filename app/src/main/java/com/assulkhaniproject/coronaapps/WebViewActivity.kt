package com.assulkhaniproject.coronaapps

import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    private val url = "https://www.worldometers.info/coronavirus"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val settings = webview.settings;

        // mengaktifkan fitur javascript dalam webview
        settings.javaScriptEnabled = true

        // mengaktifkan fitur cache
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCachePath(cacheDir.path)


        // mengaktifkan fitur zoom in dan zoom out
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = true

        // pengaturan gambar dalam webview, mau di aktifin atau tidaknya
        settings.blockNetworkImage = false

        // meload gambar secara otomatis
        settings.loadsImagesAutomatically = true


        // pengaturan lain untuk webview
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.safeBrowsingEnabled = true  // api 26
        }
        //atur pluginstate
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            settings.mediaPlaybackRequiresUserGesture = false
        }

        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.loadWithOverviewMode = true
        settings.allowContentAccess = true
        settings.setGeolocationEnabled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.allowUniversalAccessFromFileURLs = true
        }

        settings.allowFileAccess = true

        webview.fitsSystemWindows = true

        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        webview.loadUrl(url)

        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            }

            override fun onPageFinished(view: WebView, url: String) {
            }
        }

    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        }else{
            super.onBackPressed()
        }
    }
    }

