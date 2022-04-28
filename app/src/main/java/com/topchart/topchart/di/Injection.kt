package com.topchart.topchart.di

import com.mymusic.datasource.TopSongsDataSource
import com.mymusic.datasource.TopSongsRemoteDataSource
import com.mymusic.other.nework.ApiClient
import com.topchart.topchart.repository.TopSongsRepository
import com.topchart.topchart.viewmodel.TopPlayListViewModelFactory

object Injection {

    private var topSongsDataSource: TopSongsDataSource? = null
    private var topSongsRepository: TopSongsRepository? = null
    private var topPlayListVMFactory: TopPlayListViewModelFactory? = null

    private fun createTopSongsDataSource(): TopSongsDataSource {
        val dataSource = TopSongsRemoteDataSource(ApiClient)
        topSongsDataSource = dataSource
        return dataSource
    }

    private fun createTopSongsRepository(): TopSongsRepository {
        val repository = TopSongsRepository(provideDataSource())
        topSongsRepository = repository
        return repository
    }

    private fun createFactory(): TopPlayListViewModelFactory {
        val factory = TopPlayListViewModelFactory(providerRepository())
        topPlayListVMFactory = factory
        return factory
    }

    private fun provideDataSource() = topSongsDataSource ?: createTopSongsDataSource()
    private fun providerRepository() = topSongsRepository ?: createTopSongsRepository()

    fun provideViewModelFactory() = topPlayListVMFactory ?: createFactory()

    fun destroy() {
        topSongsDataSource = null
        topSongsRepository = null
        topPlayListVMFactory = null
    }
}