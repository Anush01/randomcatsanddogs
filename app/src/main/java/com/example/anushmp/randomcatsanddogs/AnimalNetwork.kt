package com.example.anushmp.randomcatsanddogs

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AnimalNetwork {

    companion object{

    var caturl:String = "https://api.thecatapi.com/"
    var dogurl:String = "https://dog.ceo/api/breeds/image/"



        fun getCatRetrofitInstance(): Retrofit{


            return Retrofit.Builder().baseUrl(caturl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build()).build()

        }

        fun getDogRetrofitInstance(): Retrofit{


            return Retrofit.Builder().baseUrl(dogurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build()).build()

        }


    }

}