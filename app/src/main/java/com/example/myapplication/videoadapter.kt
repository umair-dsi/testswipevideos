package com.example.myapplication

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_video_row.view.*

class VideoAdapter(
    val context: Context,
    val videos: ArrayList<videomodel>
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View =
            LayoutInflater.from(context).inflate(R.layout.single_video_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vm: videomodel = videos[position]
        val uri = Uri.parse(vm.videourl)
        holder.vv.setVideoURI(uri)
        holder.title.text = vm.title
        holder.desc.text = vm.desc

        holder.vv.setOnPreparedListener { mp ->
            holder.pbar.visibility = View.GONE
            mp?.start()

            val videoRatio = mp.videoWidth / mp.videoHeight.toFloat()
            val screenRatio = holder.vv.width / holder.vv.height.toFloat()

            val scale = videoRatio/screenRatio
            if(scale >= 1f)
                holder.vv.scaleX = scale
            else
                holder.vv.scaleY = scale

        }
        holder.vv.setOnCompletionListener { mp ->
            mp.start()
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vv = itemView.videoView
        val pbar = itemView.viewProgressBar
        val title = itemView.textVideoTitle
        val desc = itemView.textVideoDescription
    }
}