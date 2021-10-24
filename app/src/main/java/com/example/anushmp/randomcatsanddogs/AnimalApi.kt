package com.example.anushmp.randomcatsanddogs

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface AnimalApi {

    @GET("v1/images/search")
    fun getcat(): Call<CatResponse>

    @GET("random")
    fun getDog(): Call<DogResponse>

}