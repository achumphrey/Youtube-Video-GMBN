package com.example.youtubevideogmbn.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.youtubevideogmbn.R
import com.example.youtubevideogmbn.data.model.Item
import com.example.youtubevideogmbn.databinding.VideoListItemBinding
import com.squareup.picasso.Picasso

class VideoViewHolder (private val binding: VideoListItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindItem(videoItem: Item, listener: VideoListener) {

        binding.title = videoItem.snippet.title
        Picasso.get()
            .load(videoItem.snippet.thumbnails.default.url)
            .error(R.drawable.ic_launcher_background)
            .into(binding.videoThumb)

        itemView.setOnClickListener {
            listener.videoClickListener(videoItem)
        }
    }
}
