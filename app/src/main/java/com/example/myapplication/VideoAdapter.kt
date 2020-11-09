package com.example.myapplication

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.single_video_row.view.*

class VideoAdapter(
    val context: Context,
    val videos: ArrayList<videomodel>
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.single_video_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val vm = videos[position]
        holder.setData(context,videos[position], position)
        holder.title.text = vm.title
        holder.desc.text = vm.desc
        Log.d(MainActivity.TAG, "recyclerview onBindViewHolder at position $position")
            if(position == MainActivity.CURRENT_POSITION ){
                (context as MainActivity).changeVideo(holder, vm)
            }
//            else
//                (context as MainActivity).stopFeed()
        holder.setIsRecyclable(false)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pbar = itemView.viewProgressBar
        val title = itemView.textVideoTitle
        val desc = itemView.textVideoDescription
        val playerView = itemView.playerView

        fun setData(context: Context, vm: videomodel, position: Int){

        }
    }
}