package com.example.podcastapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class RecyclerViewAdapterResults(private val list: ArrayList<ItemsViewModelResults>) :
    RecyclerView.Adapter<RecyclerViewAdapterResults.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview, parent, false)
        return ViewHolder(v, list)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bindItems(list[position])

        val ItemsViewModelResults_ = list[position]

        var podcast = ItemsViewModelResults_.podcast

        var title_original = podcast.getString("title_original")
        var publisher_original = podcast.getString("publisher_original")
        var thumbnail = podcast.getString("thumbnail")
        var podcast_id = podcast.getString("id")


        Picasso
            .get()
            .load(thumbnail)
        .into(holder.podcastThumbnail)

        holder.titleView.text = title_original
        holder.authorView.text = publisher_original





    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View, var list: ArrayList<ItemsViewModelResults>) : RecyclerView.ViewHolder(itemView) {

        var titleView: TextView = itemView.findViewById(R.id.titleView)
        var authorView: TextView = itemView.findViewById(R.id.authorView)
        var podcastThumbnail:ImageView = itemView.findViewById(R.id.podcastThumbnail)

        init {
            //Toast.makeText(itemView.context, adapterPosition.toString(), Toast.LENGTH_SHORT).show()
            itemView.setOnClickListener() { v: View ->
                var position = adapterPosition
                val ItemsViewModelResults_ = list[position]

                var podcast = ItemsViewModelResults_.podcast

                var title_original = podcast.getString("title_original")
                var publisher_original = podcast.getString("publisher_original")
                var thumbnail = podcast.getString("thumbnail")
                var podcast_id = podcast.getString("id")

                Toast.makeText(itemView.context, title_original.toString(), Toast.LENGTH_SHORT).show()


                //(this as MainActivity).finish()
                //overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
                //(this as Activity).overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

                //finish()

                //Toast.makeText(itemView.context, adapterPosition.toString(), Toast.LENGTH_SHORT).show()

            }
        }
    }

}