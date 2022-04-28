package com.topchart.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.topchart.R
import com.topchart.topchart.view.TopPlayListFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TopPlayListFragment())
                .commitNow()
        }
    }
}