package com.example.youtubevideogmbn.repository

import com.example.youtubevideogmbn.data.model.VideoListData
import com.example.youtubevideogmbn.data.remote.VideoWebService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class VideoRepoImpl @Inject constructor(private val videoWebService: VideoWebService): VideoRepository{

    override fun getVideoListData(): Single<VideoListData> {
        return videoWebService.getVideoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}