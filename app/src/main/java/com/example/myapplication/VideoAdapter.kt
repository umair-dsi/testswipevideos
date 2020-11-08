package com.example.myapplication

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
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

        holder.setData(context,videos[position], position)
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
            title.text = vm.title
            desc.text = vm.desc

//            vv.setOnPreparedListener { mp ->
//                pbar.visibility = View.GONE
//                mp?.start()
//
//                val videoRatio = mp.videoWidth / mp.videoHeight.toFloat()
//                val screenRatio = vv.width / vv.height.toFloat()
//
//                val scale = videoRatio/screenRatio
//                if(scale >= 1f)
//                    vv.scaleX = scale
//                else
//                    vv.scaleY = scale
//            }
//            vv.setOnCompletionListener { mp ->
//                mp.start()
//            }
            if(position == MainActivity.CURRENT_POSITION )
                (context as MainActivity).changeVideo(this, vm)
            else
                (context as MainActivity).stopFeed()
        }
    }
}