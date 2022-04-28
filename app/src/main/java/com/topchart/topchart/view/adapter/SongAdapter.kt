package com.mymusic.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.topchart.R
import com.topchart.topchart.Results
import com.topchart.topchart.view.listener.OnSongClickListener

class SongAdapter(private var songs: List<Results>, private var listener: OnSongClickListener) :
    RecyclerView.Adapter<SongAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener {
            val url = songs[position].url
            if (url != null) {
                listener.onSongClick(url)
            }
        })
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(data: List<Results>) {
        songs = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvArtistName: TextView = view.findViewById(R.id.tvArtistName)
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val tvReleaseDate: TextView = view.findViewById(R.id.tvReleaseDate)
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        fun bind(song: Results) {
            tvArtistName.text = song.artistName
            tvName.text = song.name
            tvReleaseDate.text = song.releaseDate
            Glide.with(imageView.context).load(song.artworkUrl100).into(imageView)
        }
    }
}