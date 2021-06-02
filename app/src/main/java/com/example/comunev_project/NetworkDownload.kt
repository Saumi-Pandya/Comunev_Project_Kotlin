package com.example.comunev_project

import android.util.Log
import java.net.HttpURLConnection
import java.net.URL

/* function which sends HTTP request to the API. This function is called in the main activity. It takes the API
 url as an input and returns String object to the calling function.*/

fun getDataFromNetwork(url: String): String{
    val urlConnection = URL(url).openConnection()
            as HttpURLConnection
    Log.d("MainActivity", "Before fetchDataAndSave(): " + Thread.currentThread().name)
    try {
        return urlConnection.inputStream.bufferedReader().readText()
    }
    catch (e: Exception){
        val str: String = "No Internet Connection"
        return  str
    }finally {
        urlConnection.disconnect()
    }
}