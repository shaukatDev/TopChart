package com.topchart.topchart.view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.topchart.databinding.FragmentSongDetailBinding


class SongDetailFragment : Fragment() {
    private lateinit var binding: FragmentSongDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSongDetailBinding.inflate(inflater, container, false);
        initWebView()

        return binding.root
    }

    /**
     * Load Song detail url in webview
     */
    private fun initWebView() {
        val url = arguments?.getString("url").toString()
        binding.webView.loadUrl(url)
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                view?.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                setProgressVisibility(true)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                setProgressVisibility(false)
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                setProgressVisibility(false)
                val errorMessage = "Error! $error"
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                super.onReceivedError(view, request, error)
            }
        }
    }

    private fun setProgressVisibility(isShow: Boolean) {
        if (isShow) {
            binding.webView.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        } else {
            binding.webView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }

    }

}