package com.example.youtubevideogmbn.repository

import com.example.youtubevideogmbn.data.model.VideoListData
import io.reactivex.Single

interface VideoRepository {
    fun getVideoListData(): Single<VideoListData>

}