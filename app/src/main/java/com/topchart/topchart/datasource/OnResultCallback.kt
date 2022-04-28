package com.mymusic.datasource

import com.topchart.topchart.TopSongs


interface OnResultCallback<T> {
    fun onSuccess(data: TopSongs)
    fun onError(error: String?)
}