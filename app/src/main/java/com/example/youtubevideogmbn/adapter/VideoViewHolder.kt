package com.example.youtubevideogmbn.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubevideogmbn.R
import com.example.youtubevideogmbn.data.model.Item

class VideoViewHolder (item: View): RecyclerView.ViewHolder(item) {

    val imageV : ImageView = item.findViewById(R.id.videoThumb)
    val videoTitle : TextView = item.findViewById(R.id.videoTitle)

    fun bindItem(video: Item, listener: VideoListener) {

        itemView.setOnClickListener {
            listener.videoClickListener(video)
        }

    }
}
