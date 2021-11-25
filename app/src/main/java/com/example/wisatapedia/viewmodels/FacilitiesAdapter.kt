package com.example.wisatapedia.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wisatapedia.R
import android.graphics.drawable.Drawable




class FacilitiesAdapter(private val dataList : ArrayList<String>):RecyclerView.Adapter<FacilitiesAdapter.MyViewHolder>() {
    class MyViewHolder(v : View) :  RecyclerView.ViewHolder(v) {
        val mImage : ImageView = v.findViewById(R.id.ivFacilitiesList)
        val mName : TextView = v.findViewById(R.id.tvFacilitiesName)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FacilitiesAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_facilities, parent, false)
        return FacilitiesAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FacilitiesAdapter.MyViewHolder, position: Int) {
        val data = dataList[position]
        if(data.toString().lowercase() == "mushola"){
            holder.mImage.setBackgroundResource(R.drawable.ic_mushola)
            holder.mName.text = "Mushola"
        }else if(data.toString().lowercase() == "toilet"){
            holder.mImage.setBackgroundResource(R.drawable.ic_toilet)
            holder.mName.text = "Toilet"
        }else if(data.toString().lowercase() == "parkir"){
            holder.mImage.setBackgroundResource(R.drawable.ic_parkir)
            holder.mName.text = "Parkir"
        }else if(data.toString().lowercase() == "kantin"){
            holder.mImage.setBackgroundResource(R.drawable.ic_kantin)
            holder.mName.text = "kantin"
        }else{
            holder.mImage.setBackgroundResource(R.drawable.ic_rest)
            holder.mName.text = "Rest Area"
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}