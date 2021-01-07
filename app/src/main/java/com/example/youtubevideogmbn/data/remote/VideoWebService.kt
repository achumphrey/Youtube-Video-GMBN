package com.example.youtubevideogmbn.data.remote

import com.example.youtubevideogmbn.data.model.VideoListData
import io.reactivex.Single
import retrofit2.http.GET

interface VideoWebService {

    @GET("search?part=snippet,id" +
            "&order=date&maxResults=10" +
            "&key=AIzaSyBYdnb8yW4qN1HYzsc4qp3w4p0_uiMx14U" +
            "&channelId=UC_A--fhX5gea0i4UtpD99Gg")
    fun getVideoList(): Single<VideoListData>
}