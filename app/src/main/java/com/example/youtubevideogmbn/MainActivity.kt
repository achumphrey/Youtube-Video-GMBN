package com.example.youtubevideogmbn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubevideogmbn.adapter.VideoAdapter
import com.example.youtubevideogmbn.adapter.VideoListener
import com.example.youtubevideogmbn.data.model.Item
import com.example.youtubevideogmbn.data.remote.VideoClient
import com.example.youtubevideogmbn.data.remote.VideoWebService
import com.example.youtubevideogmbn.repository.VideoRepoImpl
import com.example.youtubevideogmbn.repository.VideoRepository
import com.example.youtubevideogmbn.viewmodel.VideoViewModel
import com.example.youtubevideogmbn.viewmodel.VideoViewModelFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var videoViewModel: VideoViewModel
    private lateinit var repo: VideoRepository
    private lateinit var videoWebService: VideoWebService
    private lateinit var videoAdapter: VideoAdapter
    companion object{const val INTENT_MESSAGE = "message"}
    private val videoClickListener: VideoListener = object : VideoListener {

        override fun videoClickListener(video: Item) {
            val gson = Gson()
            intent = Intent(this@MainActivity, DetailsActivity::class.java)
            intent.putExtra(INTENT_MESSAGE, gson.toJson(video))
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        videoWebService = VideoClient().retrofitInstance
        repo = VideoRepoImpl(videoWebService)

        videoViewModel = ViewModelProvider(
            this,
            VideoViewModelFactory(repo))
            .get(VideoViewModel::class.java)

        videoViewModel.fetchVideoList()

        videoViewModel.tvLiveData().observe(this, {
            videoAdapter.updateVideoList(it)
        })

        videoViewModel.errorMessage().observe(this, {
            tvErrorMessage.text = it
        })

        videoViewModel.loadingState.observe(this, {
            when (it) {
                VideoViewModel.LoadingState.LOADING -> displayProgressbar()
                VideoViewModel.LoadingState.SUCCESS -> displayVideoList()
                VideoViewModel.LoadingState.ERROR -> displayErrorMessage()
                else -> displayErrorMessage()
            }
        })
    }

    private fun setupRecyclerView() {
        videoListRecView.layoutManager = LinearLayoutManager(this@MainActivity)
        videoAdapter = VideoAdapter(mutableListOf(), videoClickListener)
        videoListRecView.adapter = videoAdapter
    }

    private fun displayErrorMessage() {
        tvErrorMessage.visibility = View.VISIBLE
        videoListRecView.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    private fun displayVideoList() {
        tvErrorMessage.visibility = View.GONE
        videoListRecView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun displayProgressbar() {
        progressBar.visibility = View.VISIBLE
        videoListRecView.visibility = View.GONE
        tvErrorMessage.visibility = View.GONE
    }
}