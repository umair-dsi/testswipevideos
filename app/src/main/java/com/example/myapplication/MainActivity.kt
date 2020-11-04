package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var videos: ArrayList<videomodel> = ArrayList()
    lateinit var adapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = VideoAdapter(this, videos)

        viewpager.adapter = adapter
    }
}