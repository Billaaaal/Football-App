package com.example.podcastapp

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.podcastsapp.MainActivity
import com.example.podcastsapp.MainActivity.Companion.mediaplayer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

//val mediaplayer = MediaPlayer()
class RecyclerViewAdapterEpisodes(private val list: ArrayList<ItemsViewModelEpisodes>) :
    RecyclerView.Adapter<RecyclerViewAdapterEpisodes.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_episode, parent, false)
        return ViewHolder(v, list)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModelResults_ = list[position]



        val context= holder.item__.getContext()

        var bottomSheetBehavior = ItemsViewModelResults_.bottom_sheet


            //Toast.makeText(itemView.context, adapterPosition.toString(), Toast.LENGTH_SHORT).show()





        holder.play_buttonEpisode.setOnClickListener(){
            Toast.makeText(context, "Hello ! ", Toast.LENGTH_SHORT).show()
            BottomSheetBehavior.from(bottomSheetBehavior).apply{
                this.state= BottomSheetBehavior.STATE_EXPANDED

                mediaplayer.reset()
                mediaplayer.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )

                mediaplayer.setDataSource("https://stitcher2.acast.com/livestitches/2051f31da799f30e85f337c1676f9b97.mp3?aid=6295dd0becb4a30012e39677&chid=9d9d4b36-2465-53da-9afe-400d44d0b8b8&ci=WSUqy19K8sscGmvwd6GmwagsEVZpiKfcNvvht9yIWpsu2nt-XDG0Qw%3D%3D&pf=rss&sv=sphinx%401.102.0&uid=8ef63e09ddf7c5c0a4909ea63db37379&Expires=1657157661864&Key-Pair-Id=K38CTQXUSD0VVB&Signature=ihso2F7mXKDF7p3qBSKFS2DrCPddEOW2glqkzzSzRmyvCmUENww6Jb8G~UQnpibvUVyRMArXVg5iy97y4XxtKXU76sOesWnE6Os46aO0Mx8Y52HSCfqnN8Nkde0e0IBOVFiXZ1YTUEI0QngO3C42LcJytNg5Jmk32m~wEH4Ll2lrbf2kf7IAJHTBj3Lw0~qsMdHQAyMzKjs3qV01eEdPMfKaj1IwQkLf61fPKRyp8Kw5BZHewJ0TZKzQ3NhPOl80HpzUHNUyLjcEDb8d4oz2R8EVrHNC5sElDzntKTtzrFQiqO7vMlE3PScJt3x0VrE9Ik6AviWid~Eg5OG1DkWjyg__")
                mediaplayer.prepare()
                mediaplayer.start()
                this.state= BottomSheetBehavior.STATE_EXPANDED

                //mediaplayer.seekTo(2160000)


            }


        }

                // Toast.makeText(itemView.context,ItemsViewModelResults_.title.getString("audio"), Toast.LENGTH_SHORT).show()
                //Toast.makeText(itemView.context, episodeTitle.text.toString(), Toast.LENGTH_SHORT).show()
                //val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                // startActivity(itemView.context, browserIntent, false)
                //val openURL = Intent(android.content.Intent.ACTION_VIEW)
                //openURL.data = Uri.parse("https://www.tutorialkart.com/")
                //startActivity(itemView.context, openURL)
                //Log.i("TAG", ItemsViewModelResults_.title.getString("audio"))


       // var podcast = ItemsViewModelResults_.podcast







        if (ItemsViewModelResults_.type == 1){
            holder.podcastTitle_.text = ItemsViewModelResults_.publisher.toString()
            holder.episodeDescription.text = Html.fromHtml(Html.fromHtml(ItemsViewModelResults_.title.getString("description")).toString())//.replace("<strong>", "").replace("</strong>", "").replace("<p>", "").replace("</p>", "")

            holder.episodeTitle.text = ItemsViewModelResults_.title.getString("title")
            val cl: Calendar = Calendar.getInstance()
            cl.setTimeInMillis(ItemsViewModelResults_.title.getString("pub_date_ms").toLong()) //here your time in miliseconds

            val date = "" + cl.get(Calendar.DAY_OF_MONTH).toString() + "/" + cl.get(Calendar.MONTH)
                .toString() + "/" + cl.get(Calendar.YEAR)
            val time = "" + cl.get(Calendar.HOUR_OF_DAY).toString() + "/" + cl.get(Calendar.MINUTE)
                .toString() + "/" + cl.get(Calendar.SECOND)
            holder.episodeDate.text = date
            var image = ItemsViewModelResults_.title.getString("image")
            Picasso
                .get()
                .load(image)
                .resize(400, 400)
                .centerCrop()
                .into(holder.episodeThumbnail)
            var duration = ItemsViewModelResults_.title.getString("audio_length_sec").toInt()
            if((duration/3600)>=1){
                var ha = NumberFormat.getInstance().format(duration/3600).toFloat()
                //var minutes = (duration/3600)
                //Log.i("TEST", "${duration.toFloat()/3600-ha.toFloat()}")

                var minutes = duration.toFloat()/3600-ha

                //Log.i("TEST", "${ha.toInt()} h ${(minutes * 60).toInt()} min")

                holder.play_buttonEpisode.setText("${ha.toInt()} h ${(minutes * 60).toInt()} min")



            }
            else{
                var duration = ItemsViewModelResults_.title.getString("audio_length_sec").toInt()
                var minutes = (duration/60)
                if (minutes == 0){
                    holder.play_buttonEpisode.setText("${duration} sec")


                }else{
                    Log.i("TEST", "HOOO "+ duration)
                    holder.play_buttonEpisode.setText("${minutes} min")

                }
                //Log.i("TEST", "${minutes} min")


            }




        }else{
            holder.podcastTitle_.text = ItemsViewModelResults_.title.getJSONObject("podcast").getString("title_original")
            holder.episodeTitle.text = ItemsViewModelResults_.title.getString("title_highlighted")
            holder.episodeDescription.text = Html.fromHtml(Html.fromHtml(ItemsViewModelResults_.title.getString("description_original")).toString())//.replace("<strong>", "").replace("</strong>", "").replace("<p>", "").replace("</p>", "")


            val cl: Calendar = Calendar.getInstance()
            cl.setTimeInMillis(ItemsViewModelResults_.title.getString("pub_date_ms").toLong()) //here your time in miliseconds

            val date = "" + cl.get(Calendar.DAY_OF_MONTH).toString() + "/" + cl.get(Calendar.MONTH)
                .toString() + "/" + cl.get(Calendar.YEAR)
            val time = "" + cl.get(Calendar.HOUR_OF_DAY).toString() + "/" + cl.get(Calendar.MINUTE)
                .toString() + "/" + cl.get(Calendar.SECOND)
            holder.episodeDate.text = date
            var image = ItemsViewModelResults_.title.getString("image")
            Picasso
                .get()
                .load(image)
                .resize(400, 400)
                .centerCrop()
                .into(holder.episodeThumbnail)
            var duration = ItemsViewModelResults_.title.getString("audio_length_sec").toInt()
            if((duration/3600)>=1){
                var ha = NumberFormat.getInstance().format(duration/3600).toFloat()
                //var minutes = (duration/3600)
                //Log.i("TEST", "${duration.toFloat()/3600-ha.toFloat()}")

                var minutes = duration.toFloat()/3600-ha

                //Log.i("TEST", "${ha.toInt()} h ${(minutes * 60).toInt()} min")

                holder.play_buttonEpisode.setText("${ha.toInt()} h ${(minutes * 60).toInt()} min")



            }
            else{
                var duration = ItemsViewModelResults_.title.getString("audio_length_sec").toInt()
                var minutes = (duration/60)
                if (minutes == 0){
                    holder.play_buttonEpisode.setText("${duration} sec")


                }else{
                    Log.i("TEST", "HOOO "+ duration)
                    holder.play_buttonEpisode.setText("${minutes} min")

                }
                //Log.i("TEST", "${minutes} min")


            }




        }

       // holder.podcastTitle_.text = ItemsViewModelResults_.title.getString("title_original")

        //holder.episodeAuthor.text = "CulturePSG"

        //holder.episodeTitle.text = "Podcast 30/05/22 : Nos trophées du PSG 2021/2022 et questions/réponses"

       // holder.episodeDescription.text = "1. Nos trophées PSG 2021/2022 (00:01:56)\n" +
                "Meilleur joueur (00:02:20)\n" +
                "Pire joueur de la saison (00:14:27)\n" +
                "Meilleur match (00:25:46) \n" +
                "Pire match (00:35:04)\n" +
                "Meilleure recrue (00:40:02)\n" +
                "Plus belle surprise (00:51:03)\n" +
                "Plus grosse déception (01:17:35)\n" +
                "\n" +
                "2. Questions/réponses (01:53:40)\n" +
                "\n" +
                "https://www.tipeee.com/culturepsg"

        //holder.play_buttonEpisode.text = "2 h 44 min" //change to real duration






    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    //the class is hodling the list view
    @SuppressLint("ClickableViewAccessibility")
    class ViewHolder(itemView: View, var list: ArrayList<ItemsViewModelEpisodes>) : RecyclerView.ViewHolder(itemView) {

        var item__ = itemView
        //val ItemsViewModelResults_ = list[position]



        var episodeThumbnail:ImageView = itemView.findViewById(R.id.episodeThumbnail)
        var podcastTitle_: TextView = itemView.findViewById(R.id.podcastTitle_)
        var episodeDate: TextView = itemView.findViewById(R.id.episodeDate)
        var episodeTitle: TextView = itemView.findViewById(R.id.episodeTitle)
        var episodeDescription: TextView = itemView.findViewById(R.id.episodeDescription)
        var play_buttonEpisode: androidx.appcompat.widget.AppCompatButton = itemView.findViewById(R.id.play_buttonEpisode)





    }

}