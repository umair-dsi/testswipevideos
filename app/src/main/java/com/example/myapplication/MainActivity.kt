package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var videos: ArrayList<videomodel> = ArrayList()
    lateinit var adapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        var v1 = videomodel("https://media.vrockk.mobi/india/videos/1601118053630-video.mp4","First Video", "This is test")
        videos.add(v1)

        var v2 = videomodel("http://access.spaceimagingme.com:9090/vdoz/tiktok/v2.mp4","2nd Video", "This is test")
        videos.add(v2)

        var v3 = videomodel("http://access.spaceimagingme.com:9090/vdoz/tiktok/v3.mp4","3rd Video", "This is test")
        videos.add(v3)

        var v4 = videomodel("http://access.spaceimagingme.com:9090/vdoz/tiktok/v4.mp4","4th Video", "This is test")
        videos.add(v4)

        var v5 = videomodel("http://access.spaceimagingme.com:9090/vdoz/tiktok/v5.mp4","5th Video", "This is test")
        videos.add(v5)

        adapter = VideoAdapter(this, videos)
        viewpager.adapter = adapter
    }
}