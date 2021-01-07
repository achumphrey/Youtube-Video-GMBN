package com.example.youtubevideogmbn.dagger

import com.example.youtubevideogmbn.data.remote.VideoWebService
import com.example.youtubevideogmbn.repository.VideoRepoImpl
import com.example.youtubevideogmbn.repository.VideoRepository
import com.example.youtubevideogmbn.viewmodel.VideoViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideViewModelFactory (repo: VideoRepository):VideoViewModelFactory{
        return  VideoViewModelFactory(repo)
    }

    @Singleton
    @Provides
    fun provideVideoRepository(webServices: VideoWebService): VideoRepository{
        return VideoRepoImpl(webServices )
    }


}
