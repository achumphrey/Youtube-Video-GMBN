package com.example.youtubevideogmbn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubevideogmbn.R
import com.example.youtubevideogmbn.data.model.Item

class VideoAdapter(private val videoList: MutableList<Item>,
                   private val listener: VideoListener):
    RecyclerView.Adapter<VideoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
         val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.vidoe_list_item, parent, false)

        return VideoViewHolder(view)
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