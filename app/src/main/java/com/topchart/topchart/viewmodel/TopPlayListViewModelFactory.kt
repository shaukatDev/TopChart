package com.topchart.topchart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.topchart.topchart.repository.TopSongsRepository


class TopPlayListViewModelFactory(private val repository: TopSongsRepository) : ViewModelProvider.Factory {
     @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TopPlayListViewModel(repository) as T
    }
}

