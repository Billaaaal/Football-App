package com.example.podcastapp


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.podcastsapp.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.listennotes.podcast_api.exception.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import java.io.IOException


class ResultsActivity : AppCompatActivity() {
    val mediaplayer = MediaPlayer() ////////////// IMPORTANT TO BE HERE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        supportActionBar?.hide()



        var fragment: Fragment? = null
        fragment = MiniPlayer()
        val args = Bundle()
        val fragmentTransaction: FragmentTransaction =supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sheet,fragment);
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        var bottom_sheet_state = 0

        var st = 0






        var bottom_sheet = findViewById<FrameLayout>(R.id.sheet)

        var bac_dim_layout = findViewById<RelativeLayout>(R.id.bac_dim_layout)





            //if (bottom_sheet_state==1){

            // }else{
            //    bottom_sheet.setOnClickListener(){

            //        BottomSheetBehavior.from(findViewById<FrameLayout>(R.id.sheet)).apply{
            //            this.state= BottomSheetBehavior.STATE_EXPANDED
            //            var bottom_sheet_state = 1



            //       }

            //    }


            //}


        //}









       // bottom_sheet.visibility = View.INVISIBLE

















        var main = R.layout.activity_results







        BottomSheetBehavior.from(findViewById<FrameLayout>(R.id.sheet)).apply{
            //this.setPeekHeight(180)
            this.setPeekHeight(0)
            this.state = BottomSheetBehavior.STATE_COLLAPSED

        }

        BottomSheetBehavior.from(findViewById<FrameLayout>(R.id.sheet)).addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            var oldOffSet = 0f
            override fun onStateChanged(bottomSheet: View, newState: Int) {


            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //Toast.makeText(this@ResultsActivity, "Detected", Toast.LENGTH_SHORT).show()
                val inRangeExpanding = oldOffSet < slideOffset
                val inRangeCollapsing = oldOffSet > slideOffset
                if (inRangeExpanding){
                    switchFragment(1, bac_dim_layout)
                    BottomSheetBehavior.from(findViewById<FrameLayout>(R.id.sheet)).apply{
                        //this.setPeekHeight(180)

                        this.state = BottomSheetBehavior.STATE_EXPANDED

                    }


                    //st = 1

                }else{
                    switchFragment(0, bac_dim_layout)

                    BottomSheetBehavior.from(findViewById<FrameLayout>(R.id.sheet)).apply{
                        //this.setPeekHeight(180)
                        this.setPeekHeight(180)
                        this.state = BottomSheetBehavior.STATE_COLLAPSED

                    }



                   //var anim = AnimationUtils.loadAnimation(this@ResultsActivity, com.google.android.material.R.anim.abc_fade_out)
                    //anim.setDuration(0.8.toLong())
                    //bac_dim_layout.startAnimation(anim)
                    //bac_dim_layout.setVisibility(View.INVISIBLE);

                    //BottomSheetBehavior.from(findViewById<FrameLayout>(R.id.sheet)).apply{
                        //this.setPeekHeight(180)
                    //    this.setPeekHeight(180)
                    //    this.state = BottomSheetBehavior.STATE_COLLAPSED

                    //}

                }

                //Toast.makeText(this@ResultsActivity, inRangeExpanding.toString(), Toast.LENGTH_SHORT).show()

                oldOffSet = slideOffset
            }
        })




        var textViewQuery = findViewById<TextView>(R.id.query)

        //var mini_player = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.mini_player)

        //mini_player.visibility = View.INVISIBLE


        var extras = intent.extras

        var query = extras!!.getString("query").toString()

        textViewQuery.text = query

        textViewQuery.setOnClickListener(){
            var intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("query_", query)
            //intent.putExtra("lon", lon)
            startActivity(intent)
            overridePendingTransition(com.google.android.material.R.anim.abc_fade_in, com.google.android.material.R.anim.abc_fade_in)
        }

        runApiPodcasts(query)    //UNCOMMENT THIS WHEN UI COMPLETED<<<<<<---------------

        //Toast.makeText(this , query, Toast.LENGTH_SHORT).show()







        //handle the JSON array in the custom adapter


        // This will pass the ArrayList to our Adapter









    }
    override fun onBackPressed (){
        var intent = Intent(this, MainActivity::class.java)
        //intent.putExtra("lat", lat)
        //intent.putExtra("lon", lon)
        startActivity(intent)
       // overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)


    }


    fun runApiPodcasts(query:String) {



        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerview.isNestedScrollingEnabled = false

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // ArrayList of class ItemsViewModel
        var Data = ArrayList<ItemsViewModelResults>()

        // This loop will create 20 Views containing
        // the image with the count of view


        //replace the fake data with recommendations from RMC per exemple with l'After Foot id




        //var urlMain = "https://listen-api.listennotes.com/api/v2/"
        var urlMain = "https://jsonkeeper.com/b/"


        val retrofit = Retrofit.Builder()
            .baseUrl(urlMain)
            .build()


        // Create Service
        val service = retrofit.create(APIService::class.java)


        var co = CoroutineScope(Dispatchers.IO).launch {

            var response =
                //service.getFixturesData(query, "1", "1")  //(formatedDate, "Europe/Paris")
                service.getFixturesData()
            //Log.d("TAG", "onResponse: ConfigurationListener::" + response.raw().request.url)

            var prettyJson = response.body().toString()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {


                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )


                    var obj = JSONObject(prettyJson)

                    Log.d("TAG", obj.toString())

                    var podcasts: JSONArray = obj.getJSONArray("podcasts")

                    var results  = ""



                    // ArrayList of class ItemsViewModel


                    for (podcast_ in 0 until podcasts.length()) {
                        var podcast: JSONObject = podcasts.getJSONObject(podcast_)
                        //Toast.makeText(this@ResultsActivity, title_original, Toast.LENGTH_SHORT).show()

                        var title_original = podcast.getString("title_original")
                        var podcast_id = podcast.getString("id")



                        //Toast.makeText(this@ResultsActivity, title_original, Toast.LENGTH_SHORT).show()



                        //DataEpisodes.add(ItemsViewModelEpisodes("podcast"))




                        //results = results + "\n" + title_original

                        Data.add(ItemsViewModelResults(podcast))

                        //handle the JSON array in the custom adapter



                    }

                    var id_ = podcasts.getJSONObject(0).getString("id")


                    Log.i("4-3-3", query)

                    val client = OkHttpClient()

                    val request = Request.Builder()
                        //.url("https://listen-api.listennotes.com/api/v2/podcasts/" + id_ + "?sort=recent_first")
                        .url("https://jsonkeeper.com/b/KSTP")
                        //.header("X-ListenAPI-Key", "c7a88e0f1a17445bb4f14b4212fa161f")
                        //.header("Accept", "application/json")
                        .build()

                    var response__ = ""

                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            e.printStackTrace()
                        }

                        override fun onResponse(call: Call, response_: Response) {


                            response_.use {

                                var response_ = response_.body!!.string()

                                val gson_ = GsonBuilder().setPrettyPrinting().create()
                                var prettyJson_ = gson.toJson(
                                    JsonParser.parseString(
                                        response_ // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                                    )
                                )


                                var obj_ = JSONObject(prettyJson_)

                                var episodes__ = obj_.getJSONArray("episodes")

                                val list = mutableListOf<JSONObject>()

                                var publisher = obj_.getString("publisher")



                                for (episode_ in 0 until episodes__.length()) {

                                    //var main_episode_id = obj_.getJSONArray("results").getJSONObject(episode_)

                                    var episode = obj_.getJSONArray("episodes").getJSONObject(episode_)

                                    list.add(episode)



                                }



                                val client = OkHttpClient()

                                val request = Request.Builder()
                                    .url("https://jsonkeeper.com/b/HMG9")
                                   //.url("https://listen-api.listennotes.com/api/v2/search?safe_mode=0&type=episode&sort_by_date=1&only_in=title , author, description&q="+ query)
                                    //.header("X-ListenAPI-Key", "c7a88e0f1a17445bb4f14b4212fa161f")
                                    //.header("Accept", "application/json")
                                    .build()

                                client.newCall(request).enqueue(object : Callback {
                                    override fun onFailure(call: Call, e: IOException) {
                                        e.printStackTrace()
                                    }

                                    override fun onResponse(call: Call, response_: Response) {


                                        response_.use {

                                            var response_ = response_.body!!.string()

                                            val gson_ = GsonBuilder().setPrettyPrinting().create()
                                            var prettyJson_ = gson.toJson(
                                                JsonParser.parseString(
                                                    response_ // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                                                )
                                            )

                                            ////move all this to another api call with recommandations


                                            //DataEpisodes.add(ItemsViewModelEpisodes("title"))

                                            runOnUiThread{
                                                val recyclerViewEpisodes = findViewById<RecyclerView>(R.id.recyclerViewEpisodesResults)

                                                recyclerViewEpisodes.isNestedScrollingEnabled = false

                                                // this creates a vertical layout Manager
                                                recyclerViewEpisodes.layoutManager = LinearLayoutManager(this@ResultsActivity, LinearLayoutManager.VERTICAL, false)


                                                var DataEpisodes = ArrayList<ItemsViewModelEpisodes>()



                                                // DataEpisodes.add(ItemsViewModelEpisodes("SUII"))

                                                //DataEpisodes.add(ItemsViewModelEpisodes(list[0], 1))



                                                //NEED TO DEBUG WHY RECYCLERVIEW SHOWS ONLY 5 ITEMS




                                                var bottom_sheet = findViewById<FrameLayout>(R.id.sheet)

                                                for (element in list){

                                                    DataEpisodes.add(ItemsViewModelEpisodes(element, 1, publisher, bottom_sheet))

                                                }


                                                var obj_ = JSONObject(prettyJson_)

                                                var episodes__ = obj_.getJSONArray("results")

                                                for (episode_ in 0 until episodes__.length()) {

                                                    //var main_episode_id = obj_.getJSONArray("results").getJSONObject(episode_)

                                                    var episode = obj_.getJSONArray("results").getJSONObject(episode_)
                                                    DataEpisodes.add(ItemsViewModelEpisodes(episode, 2, "", bottom_sheet))



                                                }

                                                var num = 0

                                                for (element in DataEpisodes){
                                                    num = num + 1
                                                    Log.i("SUIII", num.toString())
                                                }


                                                val adapterEpisodes = RecyclerViewAdapterEpisodes(DataEpisodes)

                                                // Setting the Adapter with the recyclerview
                                                recyclerViewEpisodes.adapter = adapterEpisodes
                                                recyclerViewEpisodes.setAdapter(adapterEpisodes);


                                            }




                                            //var main_episode_id = obj.getJSONArray("episodes").getJSONObject(0).getString("id")

                                            //var main_episode = obj.getJSONArray("episodes").getJSONObject(0)

                                            // pass this main_episode to next api call to add it also to data set

                                            //Toast.makeText(this@ResultsActivity, main_episode, Toast.LENGTH_SHORT)


                                            //Log.i("TAGG", main_episode_id)


                                        }
                                    }
                                })
                            }
                        }
                    })


                    //Log.d("TAG", DataEpisodes.toString())








                    // This will pass the ArrayList to our Adapter
                    val adapter = RecyclerViewAdapterResults(Data)

                    // Setting the Adapter with the recyclerview
                    recyclerview.adapter = adapter

                    //textview.text = results






                }
                else{
                    Toast.makeText(this@ResultsActivity, "", Toast.LENGTH_SHORT).show()

                }
            }




        }






    }

    fun switchFragment(state:Int, bac_dim_layout:RelativeLayout){
        var fragment: Fragment? = null
        if (state == 0){


            var anim = AnimationUtils.loadAnimation(this, com.google.android.material.R.anim.abc_fade_out)
            anim.setDuration(0.8.toLong())
            bac_dim_layout.startAnimation(anim)
            bac_dim_layout.setVisibility(View.INVISIBLE);
            fragment = MiniPlayer()




        }else{
            var anim = AnimationUtils.loadAnimation(this, com.google.android.material.R.anim.abc_fade_in)
            fragment = FullPlayer()
            anim.setDuration(0.8.toLong())
            bac_dim_layout.startAnimation(anim)
            bac_dim_layout.setVisibility(View.VISIBLE);
            bac_dim_layout.layoutParams.height = 1000
            bac_dim_layout.requestLayout();


        }

        val args = Bundle()
        val fragmentTransaction: FragmentTransaction =supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sheet,fragment);
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }


}