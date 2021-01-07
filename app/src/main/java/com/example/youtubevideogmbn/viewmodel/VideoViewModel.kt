package com.example.youtubevideogmbn.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubevideogmbn.data.model.Item
import com.example.youtubevideogmbn.repository.VideoRepository
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

class VideoViewModel(private val repo: VideoRepository) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val showData: MutableLiveData<List<Item>> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()
    val loadingState = MutableLiveData<LoadingState>()

    fun fetchVideoList(){

        loadingState.value = LoadingState.LOADING

        disposable.add(
            repo.getVideoListData()
                .subscribe({
                    if (it == null) {
                        errorMessage.value = "No Data Found!"
                        loadingState.value = LoadingState.ERROR
                    } else {
                        showData.value = it.items
                        loadingState.value = LoadingState.SUCCESS
                    }
                }, {
                    it.printStackTrace()
                    when (it) {
                        is UnknownHostException -> errorMessage.value = "No Network!"
                        else -> errorMessage.value = it.localizedMessage
                    }
                    loadingState.value = LoadingState.ERROR
                })
        )
    }

    enum class LoadingState {
        LOADING,
        SUCCESS,
        ERROR
    }

    fun tvLiveData() : MutableLiveData<List<Item>> {
        return showData
    }

    fun errorMessage(): MutableLiveData<String>{
        return errorMessage
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }


}