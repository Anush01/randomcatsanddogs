package com.example.anushmp.randomcatsanddogs

import com.google.gson.annotations.SerializedName

data class CatResponse (

    @SerializedName("breeds") val breeds : List<String>,
    @SerializedName("id") val id : String,
    @SerializedName("url") val url : String,
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int
)