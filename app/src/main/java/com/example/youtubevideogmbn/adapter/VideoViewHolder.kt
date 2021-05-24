package com.example.youtubevideogmbn.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubevideogmbn.R
import com.example.youtubevideogmbn.data.model.Item
import com.squareup.picasso.Picasso

class VideoViewHolder (viewItem: View): RecyclerView.ViewHolder(viewItem) {

    private val imageV : ImageView = viewItem.findViewById(R.id.videoThumb)
    private val videoTitle : TextView = viewItem.findViewById(R.id.videoTitle)

    fun bindItem(videoItem: Item, listener: VideoListener) {

        videoTitle.text = videoItem.snippet.title
        Picasso.get()
            .load(videoItem.snippet.thumbnails.default.url)
            .error(R.drawable.ic_launcher_background)
            .into(imageV)

        itemView.setOnClickListener {
            listener.videoClickListener(videoItem)
        }
    }
}
