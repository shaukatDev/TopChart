package com.topchart.topchart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mymusic.datasource.OnResultCallback
import com.topchart.topchart.TopSongs
import com.topchart.topchart.repository.TopSongsRepository

class TopPlayListViewModel(private val repo: TopSongsRepository) : ViewModel() {

    private val _feed = MutableLiveData<TopSongs>().apply { value = null }
    val feed: LiveData<TopSongs> = _feed

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError


    fun loadFeed() {
        _isViewLoading.value = true
        repo.getTopSongs(object : OnResultCallback<TopSongs> {
            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error ?: "error"

            }

            override fun onSuccess(data: TopSongs) {
                _isViewLoading.value = false
                _feed.value = data

            }
        })
    }
}