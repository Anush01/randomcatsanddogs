package com.example.anushmp.randomcatsanddogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.*
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var getsequential: MaterialButton
    lateinit var getparallel: MaterialButton
    lateinit var catphoto: ImageView
    lateinit var dogphoto: ImageView
    lateinit var linkdemo: TextView
    lateinit var Dogapi: AnimalApi

    var catlink: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getsequential = findViewById(R.id.getsequential)
        getparallel = findViewById(R.id.getparallel)
        catphoto = findViewById(R.id.catphoto)
        dogphoto = findViewById(R.id.dogphoto)
        linkdemo = findViewById(R.id.linkdemo)


        var dogretrofit = AnimalNetwork.getDogRetrofitInstance()

        Dogapi = dogretrofit.create(AnimalApi::class.java)




        getsequential.setOnClickListener {


            runBlocking {

                CoroutineScope(IO).launch {

                    async {
                        var url = URL("https://api.thecatapi.com/v1/images/search")
                        var conn = url.openConnection() as HttpURLConnection
                        conn.setRequestProperty("Accept", "application/json")
                        conn.requestMethod = "GET"

                        var responsecode = conn.responseCode

                        if (responsecode == HttpURLConnection.HTTP_OK) {

                            var response = conn.inputStream.bufferedReader().use { it.readText() }
                            var catlinkformain = ""
                            var catlinkformainSan = ""

                            for (i in 0 until response.length) {

                                var currentstr: String = ""

                                currentstr =
                                    currentstr + response[i] + response[i + 1] + response[i + 2]

                                if (currentstr == "url") {

                                    for (j in i until response.length - 4) {

                                        var breaker: String =
                                            "" + response[j] + response[j + 1] + response[j + 2]

                                        if (breaker == "jpg") {
                                            break
                                        }

                                        catlinkformain = catlinkformain + response[j]

                                    }




                                    break

                                }


                            }


                            for (k in 0 until catlinkformain.length) {

                                var current = "" + catlinkformain[k]

                                if (current == "h") {



                                    for (l in k until catlinkformain.length) {

                                        catlinkformainSan = catlinkformainSan + catlinkformain[l]


                                    }

                                    catlinkformainSan = catlinkformainSan + "jpg"

                                    break
                                }

                            }


                            launch(Main) {

                                catlink = catlinkformainSan

                                //linkdemo.text = catlink
                                runBlocking {
                                    Glide.with(this@MainActivity).load(catlink).placeholder(R.drawable.ic_launcher_background).into(catphoto)

                                }

                            }


                        }

                    }.await()

                }



            }

            // linkdemo.text = catlink


            CoroutineScope(IO).launch {

                Log.d("xkcd", "coroutineworked")




                val Dog: DogResponse = Dogapi.getDog().await()
                // val Cat: Response<CatResponse> = catApi.getcat().awaitResponse()


                Log.d("xkcd", Dog.message)
                //Log.d("xkcd", Cat.url)

                launch(Main) {

                    delay(2000)
                    Glide.with(this@MainActivity).load(Dog.message).into(dogphoto)
                }



            }



        }





        getparallel.setOnClickListener {


            parallel()


        }


    }

    private fun parallel() {





        CoroutineScope(IO).launch {


            var url = URL("https://api.thecatapi.com/v1/images/search")
            var conn = url.openConnection() as HttpURLConnection
            conn.setRequestProperty("Accept", "application/json")
            conn.requestMethod = "GET"

            var responsecode = conn.responseCode

            if (responsecode == HttpURLConnection.HTTP_OK) {

                var response = conn.inputStream.bufferedReader().use { it.readText() }
                var catlinkformain = ""
                var catlinkformainSan = ""

                for (i in 0 until response.length) {

                    var currentstr: String = ""

                    currentstr =
                        currentstr + response[i] + response[i + 1] + response[i + 2]

                    if (currentstr == "url") {

                        for (j in i until response.length - 4) {

                            var breaker: String =
                                "" + response[j] + response[j + 1] + response[j + 2]

                            if (breaker == "jpg") {
                                break
                            }

                            catlinkformain = catlinkformain + response[j]

                        }

                        //qwert


                        break

                    }


                }


                for (k in 0 until catlinkformain.length) {

                    var current = "" + catlinkformain[k]

                    if (current == "h") {

                        //catlinkformainSan = catlinkformainSan + current

                        for (l in k until catlinkformain.length) {

                            catlinkformainSan = catlinkformainSan + catlinkformain[l]


                        }

                        catlinkformainSan = catlinkformainSan + "jpg"

                        break
                    }

                }


                launch(Main) {

                    catlink = catlinkformainSan

                    //linkdemo.text = catlink
                    runBlocking {
                        Glide.with(this@MainActivity).load(catlink).into(catphoto)
                    }

                }

            }

            val Dog: DogResponse = Dogapi.getDog().await()



            Log.d("xkcd", Dog.message)


            launch(Main) {


                Glide.with(this@MainActivity).load(Dog.message).into(dogphoto)
            }


        }


    }

    fun setDog() {


        CoroutineScope(IO).launch {

            val Dog: DogResponse = Dogapi.getDog().await()



            Log.d("xkcd", Dog.message)


            launch(Main) {


                Glide.with(this@MainActivity).load(Dog.message).into(dogphoto)
            }



        }



    }


}