package com.example.podcastapp

import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface APIServiceEpisodes {
    // ...
    @Headers(
        "X-ListenAPI-Key: c7a88e0f1a17445bb4f14b4212fa161f",
        "Accept: application/json")
    @GET("podcasts/{id}")
    suspend fun getFixturesData(@Path("id") id: String, @Query("sort")sort:String): Response<ResponseBody>//Call<FixturesData>//Response<ResponseBody>

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
