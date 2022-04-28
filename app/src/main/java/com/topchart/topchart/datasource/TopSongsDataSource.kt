package com.mymusic.datasource

import com.topchart.topchart.TopSongs


interface TopSongsDataSource {

    fun retrieveFeed(callback: OnResultCallback<TopSongs>)
    fun cancel()
}