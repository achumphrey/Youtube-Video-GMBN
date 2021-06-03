package com.example.youtubevideogmbn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubevideogmbn.R
import com.example.youtubevideogmbn.data.model.Item
import com.example.youtubevideogmbn.databinding.VideoListItemBinding

class VideoAdapter(private val videoList: MutableList<Item>,
                   private val listener: VideoListener):
    RecyclerView.Adapter<VideoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: VideoListItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.video_list_item,
            parent,
            false)

        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bindItem(videoList[position], listener)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    fun updateVideoList(newVideoList: List<Item>){
        videoList.clear()
        videoList.addAll(newVideoList)
        notifyDataSetChanged()
    }
}