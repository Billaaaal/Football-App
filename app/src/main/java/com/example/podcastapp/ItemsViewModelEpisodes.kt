package com.example.podcastapp

import android.widget.FrameLayout
import org.json.JSONObject

data class ItemsViewModelEpisodes(val title: JSONObject, val type:Int, val publisher:String, val bottom_sheet: FrameLayout) {
}
