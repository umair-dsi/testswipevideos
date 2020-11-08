package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object{
        val TAG = "MainActivity"
        var CURRENT_POSITION: Int =0
    }
    var videos: ArrayList<videomodel> = ArrayList()
    lateinit var adapter: VideoAdapter
    private var player: SimpleExoPlayer? = null
    var playbackStateListener: PlaybackStateListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        var v1 = videomodel(
            "https://v77.tiktokcdn.com/0168a22c353ba4ac092af98ae8460b94/5fa77b19/video/tos/alisg/tos-alisg-pve-0037c001/505f5ec0966e4ea48c3fabe468056e63/",
            "First Video",
            "This is test"
        )
        videos.add(v1)

        var v2 = videomodel(
            "https://media.vrockk.mobi/india/videos/1601118053630-video.mp4",
            "2nd Video",
            "This is test"
        )
        videos.add(v2)

        var v3 = videomodel(
            "http://access.spaceimagingme.com:9090/vdoz/tiktok/v3.mp4",
            "3rd Video",
            "This is test"
        )
        videos.add(v3)

        var v4 = videomodel(
            "http://access.spaceimagingme.com:9090/vdoz/tiktok/v4.mp4",
            "4th Video",
            "This is test"
        )
        videos.add(v4)

        var v5 = videomodel(
            "http://access.spaceimagingme.com:9090/vdoz/tiktok/v5.mp4",
            "5th Video",
            "This is test"
        )
        videos.add(v5)

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when(state){
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        Log.d(TAG, state.toString())
                        if(CURRENT_POSITION != viewpager.currentItem){
                            stopFeed()
                        }
                        CURRENT_POSITION = viewpager.currentItem

                    }
                }
            }
        })

        adapter = VideoAdapter(this, videos)
        viewpager.adapter = adapter
    }

    fun changeVideo(holder: VideoAdapter.ViewHolder, vm: videomodel){
        if (player == null){
            initializePlayer()
        }
        holder.playerView.player = player
        if(playbackStateListener!=null)
            player!!.removeListener(playbackStateListener!!);
        playbackStateListener = PlaybackStateListener(holder)
        player!!.addListener(playbackStateListener!!)

        val mediaItem: MediaItem = MediaItem.fromUri(vm.videourl)
        player!!.setMediaItem(mediaItem)

        prepareExoplayer()
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
    }

    private fun prepareExoplayer() {
        try {
            player!!.playWhenReady = true
            player!!.prepare()

        } catch (e: Exception) {

        }
    }

    fun stopFeed() {
        try {
            player!!.stop()
        } catch (e: Exception) {

        }
    }

    private fun releaseExoplayer() {
        try {
            player!!.release()
            if(playbackStateListener!=null)
                player!!.removeListener(playbackStateListener!!);
            player = null
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releaseExoplayer();
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releaseExoplayer();
        }
    }

     class PlaybackStateListener (
         private val vh: VideoAdapter.ViewHolder
     ): Player.EventListener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            var stateString: String
            when (playbackState) {
                ExoPlayer.STATE_IDLE -> {
                    stateString = "ExoPlayer.STATE_IDLE      -"
                }
                ExoPlayer.STATE_BUFFERING -> {
                    stateString = "ExoPlayer.STATE_BUFFERING -"
                    vh.pbar.visibility = View.VISIBLE
                }
                ExoPlayer.STATE_READY -> {
                    stateString = "ExoPlayer.STATE_READY     -"
                    vh.pbar.visibility = View.GONE
                    vh.playerView.visibility = View.VISIBLE
                }
                ExoPlayer.STATE_ENDED -> {
                    stateString = "ExoPlayer.STATE_ENDED     -"
                    vh.playerView.player!!.seekTo(0)
                }
                else -> {
                    stateString = "UNKNOWN_STATE             -"
                }
            }
            Log.d(TAG, "changed state to $stateString")
        }
    }
}