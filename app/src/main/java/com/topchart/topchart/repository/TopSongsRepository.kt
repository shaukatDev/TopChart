package com.topchart.topchart.repository


import com.mymusic.datasource.OnResultCallback
import com.mymusic.datasource.TopSongsDataSource
import com.topchart.topchart.TopSongs


class TopSongsRepository(private val dataSource: TopSongsDataSource) {



    fun getTopSongs(callback: OnResultCallback<TopSongs>) {
        dataSource.retrieveFeed(callback )
    }

    fun cancel() {
        dataSource.cancel()
    }
}