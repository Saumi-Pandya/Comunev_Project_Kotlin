package com.example.comunev_project

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import org.json.JSONArray
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainActivity : AppCompatActivity() {



    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val parse_data_button = findViewById<Button>(R.id.parse_json)


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
            val jsonArray = jsonObj.getJSONArray("results")

            for (i in 0 until jsonArray.length()) {
                val user = parseUser(jsonArray.getJSONObject(i))
                users.add(user)
            }

            setRecyclerView(users)

           
        }
    }

    private fun parseUser(jsonObject: JSONObject): User{
            val userObj = jsonObject.getJSONObject("name")
            val user = User(userObj.getString("title"),userObj.getString("first"),userObj.getString("last"))

            return user
    }

    private fun setRecyclerView(nameList:List<User>){
        val recyView = findViewById<RecyclerView>(R.id.recy_view)
        recyView.apply {
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter = name_adapter(nameList)
            setHasFixedSize(true)
        }
        recyView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
    }
}