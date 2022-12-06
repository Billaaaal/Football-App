package com.example.podcastapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.hide()

        var searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)


        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();

        var extras = intent.extras

        if(extras!= null){
            var query = extras!!.getString("query_").toString()
            searchView.setQuery(query, false)

            //Toast.makeText(this, query, Toast.LENGTH_SHORT).show()


        }







        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                //Toast.makeText(this@SearchActivity , p0.toString(), Toast.LENGTH_SHORT).show()
                var intent = Intent(this@SearchActivity, ResultsActivity::class.java)
                intent.putExtra("query", query)
                //intent.putExtra("lon", lon)
                startActivity(intent)
               // overridePendingTransition(com.google.android.material.R.anim.abc_fade_in, com.google.android.material.R.anim.abc_fade_in)


                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }


}