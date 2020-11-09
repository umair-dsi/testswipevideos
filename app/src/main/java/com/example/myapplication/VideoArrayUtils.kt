package com.example.myapplication

class VideoArrayUtils {
    companion object{
        fun videoArray() : ArrayList<videomodel> {
            var i = 1
            val urls: ArrayList<String> = ArrayList()
            urls.add("https://media.vrockk.mobi/videos/1600928935049-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601118053630-video.mp4")
            urls.add("https://media.vrockk.mobi/videos/1601015664263-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601115085223-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601115296502-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601116502964-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601117004663-video.mp4")
            urls.add("https://media.vrockk.mobi/videos/1601043433032-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601116372980-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601116627020-video.mp4")
            urls.add("https://media.vrockk.mobi/videos/1600928251157-video.mp4")
            urls.add("https://media.vrockk.mobi/videos/1600938993113-video.mp4")
            urls.add("https://media.vrockk.mobi/videos/1601033066870-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601110214417-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601110392764-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601110477302-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601110883623-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601111238665-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601114632997-video.mp4")
            urls.add("https://media.vrockk.mobi/india/videos/1601115915409-video.mp4")

            for (url: String in urls){
                val v = videomodel(
                    url,
                    "Video $i",
                    "This is test description $i"
                )
                videos.add(v)
                i += 1
            }

            return videos
        }
        var videos: ArrayList<videomodel> = ArrayList()
    }
}