package com.example.comunev_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// custom adapter class to populate recycler view
class name_adapter(val user_list:List<User>):RecyclerView.Adapter<name_adapter.nameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): name_adapter.nameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.name_item,parent,false)
        return nameViewHolder(itemView)
    }

    inner class nameViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        val nameview: TextView = itemView.findViewById(R.id.nameview)

        override fun onClick(p0: View?) {
        }

    }


    override fun getItemCount(): Int {
        return user_list.size;
    }

    override fun onBindViewHolder(holder: name_adapter.nameViewHolder, position: Int) {
        val currentItem = user_list[position]
        with(holder){
            var conc_name:String =  currentItem.title+" "+currentItem.first+" "+currentItem.last
            nameview.text = conc_name
        }
    }
}