package com.topchart.topchart.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mymusic.datasource.OnResultCallback
import com.mymusic.datasource.TopSongsDataSource
import com.topchart.topchart.Results
import com.topchart.topchart.TopSongs
import com.topchart.topchart.repository.TopSongsRepository
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

class TopPlayListViewModelTest {

    @Mock
    private lateinit var dataSource: TopSongsDataSource
    private lateinit var viewModel: TopPlayListViewModel
    private lateinit var repository: TopSongsRepository

    @Mock
    private lateinit var context: Application
    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OnResultCallback<TopSongs>>

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderMuseumsObserver: Observer<TopSongs>

    private lateinit var emptyFeed: TopSongs
    private lateinit var fullFeed: TopSongs


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        MockitoAnnotations.openMocks(this)
        Mockito.`when`(context.applicationContext).thenReturn(context)

        repository = TopSongsRepository(dataSource)
        viewModel = TopPlayListViewModel(repository)
        mockData()
        setupObservers()
    }


    @Test
    fun `retrieve top songs with ViewModel and Repository returns empty data`() {
        with(viewModel) {
            loadFeed()
            isViewLoading.observeForever(isViewLoadingObserver)
            feed.observeForever(onRenderMuseumsObserver)
        }

        Mockito.verify(dataSource, Mockito.times(1)).retrieveFeed( capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(emptyFeed)

        assertNotNull(viewModel.isViewLoading.value)
        assertTrue(viewModel.feed.value?.feed?.songs?.size == 0)
    }

    @Test
    fun `retrieve top songs with ViewModel and Repository returns full data`() {
        with(viewModel) {
            loadFeed()
            isViewLoading.observeForever(isViewLoadingObserver)
            feed.observeForever(onRenderMuseumsObserver)
        }

        Mockito.verify(dataSource, Mockito.times(1)).retrieveFeed(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(fullFeed)

        assertNotNull(viewModel.isViewLoading.value)
        assertTrue(viewModel.feed.value?.feed?.songs?.size == 1)
    }


    @Test
    fun `retrieve top songs with ViewModel and Repository returns an error`() {
        with(viewModel) {
            loadFeed()
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        Mockito.verify(dataSource, Mockito.times(1)).retrieveFeed( capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("An error occurred")
        assertNotNull(viewModel.isViewLoading.value)
        assertNotNull(viewModel.onMessageError.value)
    }

    private fun setupObservers() {
        isViewLoadingObserver = Mockito.mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = Mockito.mock(Observer::class.java) as Observer<Any>
        emptyListObserver = Mockito.mock(Observer::class.java) as Observer<Boolean>
        onRenderMuseumsObserver = Mockito.mock(Observer::class.java) as Observer<TopSongs>
    }

    private fun mockData() {
        emptyFeed = TopSongs()
        fullFeed = TopSongs()

        val mockSongsList: ArrayList<Results> = ArrayList()
        mockSongsList.add(
            Results(
                "Jack Harlow",
                "myid",
                "First Class"
            )
        )

        fullFeed.feed?.songs  = mockSongsList

    }

    private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
}