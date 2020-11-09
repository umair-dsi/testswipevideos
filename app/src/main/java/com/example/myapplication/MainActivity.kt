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
        val TAG = "MainActivityLog"
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
            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        )

        var v1 = videomodel(
            "https://media.vrockk.mobi/india/videos/1601118053630-video.mp4",
            "First Video",
            "This is test"
        )
        videos.add(v1)

        var v2 = videomodel(
            "https://v16m.tiktokcdn.com/cd6877ff43ca305f106ded8639f68aad/5fa8aec2/video/tos/useast2a/tos-useast2a-pve-0068/e3bccf78134a4e30800198facb92c92d/?a=1233&br=3000&bt=1500&cr=0&cs=0&cv=1&dr=0&ds=3&er=&l=20201108205133010189194081000396D0&lr=tiktok_m&mime_type=video_mp4",

            "2nd Video",
            "This is test"
        )
        videos.add(v2)

        var v3 = videomodel(
            "https://v16m.tiktokcdn.com/aeba7dcc9ee981f7ebcaef2e732601d3/5fa8aec2/video/tos/alisg/tos-alisg-pve-0037c001/0281f1b7f379469ca97a400c11d4551c/?a=1233&br=4196&bt=2098&cr=0&cs=0&cv=1&dr=0&ds=3&er=&l=20201108205133010189194081000396D0&lr=tiktok_m&mime_type=video_mp4&qs=0&rc=Mzw1bHRpbjp5eDMzaTczM0ApNTM8OTY3N2Q1Nzg0NTtkOGdzbjMxNG9jbS9fLS02MTRzc2E1LTU0LWNgLi5gMjA0MC86Yw%3D%3D&vl=&vr=",
            "3rd Video",
            "This is test"
        )
        videos.add(v3)

        var v4 = videomodel(
            "https://v16m.tiktokcdn.com/d55f095c17217802c7fff10128b0cb8b/5fa8aec0/video/tos/alisg/tos-alisg-pve-0037c001/dfdf2f65a32f40409474a6cf2802d666/?a=1233&br=3200&bt=1600&cr=0&cs=0&cv=1&dr=0&ds=3&er=&l=20201108205133010189194081000396D0&lr=tiktok_m&mime_type=video_mp4&qs=0&rc=MztuZ29rNnQ1dzMzaDczM0ApNTQ6Z2c8O2Q4N2hnaWdmPGdoMzYvNGYxb2dfLS0vMTRzcy82Ni9iYDMwYTY0YC81YWA6Yw%3D%3D&vl=&vr=",
            "4th Video",
            "This is test"
        )
        videos.add(v4)

        var v5 = videomodel(
            "https://v16m.tiktokcdn.com/a02a477525c617ebf4c8660ad2dd61d1/5fa8aef0/video/tos/alisg/tos-alisg-pve-0037c001/c7428bcea9bf4b85b11437391fa1c61d/?a=1233&br=1022&bt=511&cr=0&cs=0&dr=0&ds=2&er=&l=20201108205133010189194081000396D0&lr=tiktok_m&mime_type=video_mp4&qs=0&rc=M2k8cWRscHZ4eDMzZzczM0ApZWZoZDs8NGVmN2Q2OTxoPGcvX2g1Ly4xMmBfLS1hMTRzczExM15gLzReYjEvYTJhLmM6Yw%3D%3D&vl=&vr=",
            "5th Video",
            "This is test"
        )
        videos.add(v5)
//        viewpager.offscreenPageLimit = 1
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when(state){
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        Log.d(TAG, "onPageScrollStateChanged SCROLL_STATE_IDLE $state")
//                        if(CURRENT_POSITION != viewpager.currentItem){
//
//                            stopFeed()
//                        }
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                CURRENT_POSITION = position
                adapter.notifyDataSetChanged()
                Log.d(TAG, "onPageSelected CURRENT_POSITION = $position")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
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
        player!!.repeatMode = Player.REPEAT_MODE_ONE
    }

    private fun prepareExoplayer() {
        try {
            player!!.playWhenReady = true
            player!!.prepare()

        } catch (e: Exception) {

        }
    }

    fun isPlaying(): Boolean {
        try {
            return player!!.isPlaying
        } catch (e: Exception) {

        }
        return false
    }

    fun pauseFeed() {
        try {
            player!!.pause()
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
            Log.d(TAG, "releaseExoplayer exception $e")
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
            Log.d(TAG, "PlaybackStateListener changed state to $stateString")
        }
    }
}