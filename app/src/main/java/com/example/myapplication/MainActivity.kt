package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
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



        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when(state){
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        Log.d(TAG, "onPageScrollStateChanged SCROLL_STATE_IDLE $state")
                        playFeed()
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        pauseFeed()
                    }
                    ViewPager2.SCROLL_STATE_SETTLING -> {

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

        videos = VideoArrayUtils.videoArray()
        adapter = VideoAdapter(this, videos)
        viewpager.adapter = adapter
    }

    fun changeVideo(holder: VideoAdapter.ViewHolder, vm: videomodel){
        Log.d(TAG, "changeVideo called CURRENT_POSITION = $CURRENT_POSITION Current Video = ${vm.title}")
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
        player!!.seekTo(0)
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

    fun playFeed() {
        try {
            player!!.play()
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

         override fun onPlayerError(error: ExoPlaybackException) {
             Log.d(TAG, "PlaybackStateListener onPlayerError $error")
         }

         override fun onIsLoadingChanged(isLoading: Boolean) {
             Log.d(TAG, "PlaybackStateListener onIsLoadingChanged $isLoading")
         }

         override fun onTracksChanged(
             trackGroups: TrackGroupArray,
             trackSelections: TrackSelectionArray
         ) {
             Log.d(TAG, "PlaybackStateListener onTracksChanged")
         }

         override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
             Log.d(TAG, "PlaybackStateListener onPlaybackParametersChanged $playbackParameters")
         }

         override fun onPositionDiscontinuity(reason: Int) {
             when(reason) {
                 Player.DISCONTINUITY_REASON_INTERNAL -> {
                     Log.d(TAG, "PlaybackStateListener onPositionDiscontinuity DISCONTINUITY_REASON_INTERNAL")
                 }
                 Player.DISCONTINUITY_REASON_AD_INSERTION -> {
                     Log.d(TAG, "PlaybackStateListener onPositionDiscontinuity DISCONTINUITY_REASON_AD_INSERTION")
                 }
                 Player.DISCONTINUITY_REASON_PERIOD_TRANSITION -> {
                     Log.d(TAG, "PlaybackStateListener onPositionDiscontinuity DISCONTINUITY_REASON_PERIOD_TRANSITION")
                 }
                 Player.DISCONTINUITY_REASON_SEEK -> {
                     Log.d(TAG, "PlaybackStateListener onPositionDiscontinuity DISCONTINUITY_REASON_SEEK")
                 }
                 Player.DISCONTINUITY_REASON_SEEK_ADJUSTMENT -> {
                     Log.d(TAG, "PlaybackStateListener onPositionDiscontinuity DISCONTINUITY_REASON_SEEK_ADJUSTMENT")
                 }
             }
         }
    }
}