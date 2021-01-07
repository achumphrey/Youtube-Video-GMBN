package com.example.youtubevideogmbn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.youtubevideogmbn.repository.VideoRepository

@Suppress("UNCHECKED_CAST")
class VideoViewModelFactory(private val repo: VideoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VideoViewModel(repo) as T
    }
}