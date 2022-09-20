package com.example.simplewebview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.simplewebview.factories.DotenvFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.initWebViewSwipeRefreshLayout()
        this.initWebView()
    }

    private fun initWebView() {
        this.initWebViewSettings()
        this.initWebViewClient()
        this.webView.settings.javaScriptEnabled = true
        this.webView.loadUrl(DotenvFactory.getInstance()["WEBVIEW_URL"]);
    }

    private fun initWebViewSettings() {
        this.webView.settings.javaScriptEnabled = true
        this.webView.settings.domStorageEnabled = true
        this.webView.settings.builtInZoomControls = true
        this.webView.settings.allowFileAccess = true
        this.webView.settings.allowContentAccess = true
    }

    private fun initWebViewClient() {
        this.webView.webChromeClient = object : WebChromeClient() {}
        this.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                webViewSwipeRefreshLayout.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webViewSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun initWebViewSwipeRefreshLayout() {
        this.webViewSwipeRefreshLayout.setOnRefreshListener {
            this.webView.reload()
        }
    }

    override fun onBackPressed() {
        if (this.webView.canGoBack())
            this.webView.goBack()
        else
            super.onBackPressed()
    }
}