package com.example.youtubevideogmbn.data.remote

import com.example.youtubevideogmbn.data.Constants
import com.example.youtubevideogmbn.data.model.VideoListData
import io.reactivex.Single
import retrofit2.http.GET

interface VideoWebService {

    @GET(Constants.ENDPOINT_URL)
    fun getVideoList(): Single<VideoListData>
}