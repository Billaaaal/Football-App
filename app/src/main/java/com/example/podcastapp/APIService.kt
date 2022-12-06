package com.example.podcastapp

import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface APIService {
    // ...
    //@Headers(
    //   "X-ListenAPI-Key: c7a88e0f1a17445bb4f14b4212fa161f",
    //   "Accept: application/json")
    //@GET("typeahead")
    @GET("M8NW")
    //suspend fun getFixturesData(@Query("q") query: String, @Query("show_podcasts") show_podcasts: String, @Query("show_genres") show_genres: String): Response<ResponseBody>//Call<FixturesData>//Response<ResponseBody>
    suspend fun getFixturesData(): Response<ResponseBody>//Call<FixturesData>//Response<ResponseBody>

    // ...

}

// object FixturesDataInstance {
//     val getFixturesData: APIService
//
//     val getUserData:GetUserData
//     init{
//        banget}
//     val retrofit=Retrofit.Builder()
//      .baseUrl(baseUrl)
//      .addConverterFactory(GsonConverterFactory.create())
//      .build()
//  getUserData
// }
