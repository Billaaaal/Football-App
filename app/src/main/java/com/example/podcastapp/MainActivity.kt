package com.example.podcastsapp

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.podcastapp.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
   // val mediaplayer = MediaPlayer() ////////////// IMPORTANT TO BE HERE

    companion object {
        val mediaplayer = MediaPlayer()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        //var search_bar = findViewById<SearchView>(R.id.search_bar)

        //search_bar.setQueryHint("Enter your address");
        //search_bar.setIconified(false);
        //search_bar.clearFocus();

        var search_bar = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.search_bar)

        search_bar.setOnClickListener(){
            var intent = Intent(this@MainActivity, SearchActivity::class.java)
            //intent.putExtra("lat", lat)
            //intent.putExtra("lon", lon)
            startActivity(intent)
            overridePendingTransition(com.google.android.material.R.anim.abc_fade_in, com.google.android.material.R.anim.abc_fade_in)



        }





        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerview.isNestedScrollingEnabled = false

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // ArrayList of class ItemsViewModel
        val Data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view


        //replace the fake data with recommendations from RMC per exemple with l'After Foot id


        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))
        Data.add(ItemsViewModel(""))





        // This will pass the ArrayList to our Adapter
        val adapter = RecyclerViewAdapter(Data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter



       ///////////////////////////////////////////////
       /////////////////////////

       val recyclerViewEpisodes = findViewById<RecyclerView>(R.id.recyclerViewEpisodes)
       recyclerViewEpisodes.isNestedScrollingEnabled = false

       // this creates a vertical layout Manager
       recyclerViewEpisodes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

       // ArrayList of class ItemsViewModel
       var DataEpisodes = ArrayList<ItemsViewModelEpisodes>()

       //DataEpisodes.add(ItemsViewModelEpisodes("podcast"))
       //DataEpisodes.add(ItemsViewModelEpisodes("podcast"))
       //DataEpisodes.add(ItemsViewModelEpisodes("podcast"))
       //DataEpisodes.add(ItemsViewModelEpisodes("podcast"))
       //DataEpisodes.add(ItemsViewModelEpisodes("podcast"))

       //handle the JSON array in the custom adapter


       // This will pass the ArrayList to our Adapter
       val adapterEpisodes = RecyclerViewAdapterEpisodes(DataEpisodes)

       // Setting the Adapter with the recyclerview
       recyclerViewEpisodes.adapter = adapterEpisodes


       // mediaplayer.reset()


       // mediaplayer.setAudioAttributes(
       //AudioAttributes.Builder()
       //    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
       //    .build()
       // )



       //  mediaplayer.setDataSource("https://stitcher.acast.com/livestitches/6295dd0becb4a30012e39677/9bb0dc8e6f24892db3c3429d5fc45534.mp3?aid=6295dd0becb4a30012e39677&chid=9d9d4b36-2465-53da-9afe-400d44d0b8b8&ci=MkX53_9qhgx53E_OP9bc1uXR2OYJNEXyfE2NCtx0mwVyYbSDSDKZGQ%3D%3D&pf=rss&sv=sphinx%401.94.1&uid=dada398f85a9b3c685f12d3247f24bff&Expires=1654366296&Signature=RpKyIOwkVNwjzJTG6IoS7BeuIAcvJFCTNIjswn7s6t6Tor8Jww4rJhRHQBl9MKbehorD0C81soLBntXyUou1MNU35ZjbxbfMmgLFv8KkhpZ6Yknbt0BZuHU7WsdBdb7u7pCn9u5rWdvc5kliZM45pbV83G5WM2PKfdjmzvFKL2yjqiwQILcgXXNXzMR7YhkUQEP-QpvtXQ5h5rNg5m9N1m8J2hsfpsNAtOAq6Kc1b3zuvW1lQliIyE0fvHc9Ebn7zg7J9PtXhfN-qscvQ9HsQRmfzgJcEApXO6i-f%7EvtFl0TfpWOW6nFJMvfqsuQ5AZb-8FX2%7EB%7Ec3igDwvFhW7KNw__&Key-Pair-Id=APKAJXAFARUOTJQ3BLOQ")
       // mediaplayer.prepare()
        //mediaplayer.start()
        //mediaplayer.seekTo(2160000)

        //fun onStop(mediaPlayer:MediaPlayer) {
        //    super.onStop()
        //    mediaPlayer.stop()
        //    mediaPlayer.release()
        //}



    }

}