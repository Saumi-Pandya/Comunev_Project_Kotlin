package com.example.comunev_project

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import org.json.JSONArray
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val parse_data_button = findViewById<Button>(R.id.parse_json)
    val display_data = findViewById<TextView>(R.id.parsed_data)

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parse_data_button
            .setOnClickListener{
                parseJson()
            }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun parseJson(){
        val users = mutableListOf<User>()
        lifecycleScope.launch {
            val googleData = withContext(Dispatchers.IO) {
                getDataFromNetwork("https://randomuser.me/api/?results=100&inc=name")
            }

            val jsonObj = JSONObject(googleData)
            val jsonArray = JSONArray(jsonObj.getJSONArray("results"))

            for (i in 0 until jsonArray.length()) {
                val user = parseUser(jsonArray.getJSONObject(i))
                users.add(user)
            }

            display_data.text = users.joinToString ("\n"){ it.title }
        }
    }

    private fun parseUser(jsonObject: JSONObject): User{
            val userObj = jsonObject.getJSONObject("name")
            val user = User(userObj.getString("title"),userObj.getString("first"),userObj.getString("last"))

            return user
    }
}