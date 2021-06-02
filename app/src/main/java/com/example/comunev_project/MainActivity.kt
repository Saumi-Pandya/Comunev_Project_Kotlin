package com.example.comunev_project

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
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

        //View binding
        val pbar = findViewById<ProgressBar>(R.id.pbar)
        val parse_data_button = findViewById<Button>(R.id.parse_json)
        val errorview = findViewById<TextView>(R.id.errorview)
        errorview.text = "Data will be displayed here in a recycler view"

        pbar.isVisible=false;

        //setting up onClick listener
        parse_data_button
            .setOnClickListener {
                errorview.isVisible=false
                pbar.isVisible=true
                parseJson()
            }
    }

    
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun parseJson() {
        val users = mutableListOf<User>()
        val errorview = findViewById<TextView>(R.id.errorview)
        val pbar = findViewById<ProgressBar>(R.id.pbar)



        lifecycleScope.launch {
            val googleData = withContext(Dispatchers.IO) {
                getDataFromNetwork("https://randomuser.me/api/?results=100&inc=name")
            }

            pbar.isVisible=false

            if (googleData == "No Internet Connection") {
                Toast.makeText(this@MainActivity,"$googleData",Toast.LENGTH_SHORT).show()

            }
            else {
                val jsonObj = JSONObject(googleData)
                val jsonArray = jsonObj.getJSONArray("results")

                for (i in 0 until jsonArray.length()) {
                    val user = parseUser(jsonArray.getJSONObject(i))
                    users.add(user)
                }
                pbar.isEnabled=false
                setRecyclerView(users)
            }


        }
    }

    private fun parseUser(jsonObject: JSONObject): User {
        val userObj = jsonObject.getJSONObject("name")
        val user =
            User(userObj.getString("title"), userObj.getString("first"), userObj.getString("last"))

        return user
    }

    private fun setRecyclerView(nameList: List<User>) {
        val recyView = findViewById<RecyclerView>(R.id.recy_view)
        recyView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = name_adapter(nameList)
            setHasFixedSize(true)
        }
        recyView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
