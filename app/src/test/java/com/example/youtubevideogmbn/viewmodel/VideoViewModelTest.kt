package com.example.youtubevideogmbn.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.youtubevideogmbn.data.model.*
import com.example.youtubevideogmbn.repository.VideoRepository
import com.nhaarman.mockitokotlin2.atLeast
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class VideoViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var videoList: VideoListData
    private lateinit var snippet: Snippet
    private lateinit var thumbnails: Thumbnails
    private var itemList = mutableListOf<Item>()
    private val showDataObserver: Observer<List<Item>> = mock()
    private val loadingStateLDObserver: Observer<VideoViewModel.LoadingState> = mock()
    private lateinit var videoViewModel: VideoViewModel
    private val errorMessageLDObserver: Observer<String> = mock()

    @Mock
    lateinit var videoRepo: VideoRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        videoViewModel = VideoViewModel(videoRepo)
        videoViewModel.tvLiveData().observeForever(showDataObserver)
        videoViewModel.errorMessage().observeForever(errorMessageLDObserver)
        videoViewModel.loadingState.observeForever(loadingStateLDObserver)
        thumbnails = Thumbnails(Default(2, "any", 3), High(2, "any", 3), Medium(1, "any", 3))
        snippet = Snippet("any", "any", "any", "any","any", "any", thumbnails, "any")
        itemList.add(Item("any", Id("any", "any"), "any", snippet))
        videoList = VideoListData("",
            itemList, "", "",
            PageInfo(1, 2),
            "")


    }

    @Test
    fun fetchVideo_ReturnData_WithSuccess() {

        Mockito.`when`(videoRepo.getVideoListData()).thenReturn(Single.just(videoList))
        videoViewModel.fetchVideoList()

        verify(videoRepo, atLeast(1)).getVideoListData()
        verify(showDataObserver, atLeast(1)).onChanged(itemList)
        verify(errorMessageLDObserver, atLeast(0)).onChanged("Any")
        verify(loadingStateLDObserver, atLeast(1))
            .onChanged(VideoViewModel.LoadingState.SUCCESS)
    }

    @Test
    fun fetchVideo_NoReturnOfData_EmptyList() {
        val videoObject = VideoListData("", emptyList(), "", "", PageInfo(0, 0), "")

        Mockito.`when`(videoRepo.getVideoListData()).thenReturn(Single.just(videoObject))
        videoViewModel.fetchVideoList()

        verify(videoRepo, atLeast(1)).getVideoListData()
        verify(showDataObserver, atLeast(0)).onChanged(emptyList())
        verify(errorMessageLDObserver, atLeast(1)).onChanged("No Data Found!")
        verify(loadingStateLDObserver, atLeast(1))
            .onChanged(VideoViewModel.LoadingState.ERROR)

    }

    @Test
    fun fetchShow_NoReturnData_NoNetwork() {

        Mockito.`when`(videoRepo.getVideoListData()).thenReturn(Single.error(
            UnknownHostException("No Network!")))

        videoViewModel.fetchVideoList()

        verify(videoRepo, atLeast(1)).getVideoListData()
        verify(showDataObserver, atLeast(0)).onChanged(mutableListOf())
        verify(errorMessageLDObserver, atLeast(1)).onChanged("No Network!")
        verify(loadingStateLDObserver, atLeast(1))
            .onChanged(VideoViewModel.LoadingState.ERROR)
    }

    @Test
    fun fetchShow_NoReturnData_WithError() {

        Mockito.`when`(videoRepo.getVideoListData()).thenReturn(Single.error(
            RuntimeException("Something Wrong, no blank or empty field")))

        videoViewModel.fetchVideoList()

        verify(videoRepo, atLeast(1)).getVideoListData()
        verify(showDataObserver, atLeast(0)).onChanged(mutableListOf())
        verify(errorMessageLDObserver, atLeast(1))
            .onChanged("Something Wrong, no blank or empty field")
        verify(loadingStateLDObserver, atLeast(1))
            .onChanged(VideoViewModel.LoadingState.ERROR)

    }
}