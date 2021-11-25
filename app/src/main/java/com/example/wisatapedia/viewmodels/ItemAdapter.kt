package com.example.wisatapedia.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wisatapedia.R
import com.example.wisatapedia.activity.DashBoardActivity
import com.example.wisatapedia.activity.DetailActivity
import com.example.wisatapedia.databinding.FragmentSearchBinding
import com.example.wisatapedia.view.SearchFragment
import com.example.wisatapedia.view.ViewFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ItemAdapter(private val dataList : ArrayList<Items>, cxt: Context?) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {
    lateinit var mStorageReference: StorageReference
    private val context : Context = cxt!!
    private val fs : SearchFragment = SearchFragment()
    class MyViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.tvItemName)
        val location : TextView = itemView.findViewById(R.id.tvItemLocation)
        val img : ImageView = itemView.findViewById(R.id.ivTumbnail)
        val rBar : RatingBar = itemView.findViewById(R.id.rbarItem)
        val ratingTitle : TextView = itemView.findViewById(R.id.tvRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        mStorageReference = FirebaseStorage.getInstance().getReference()
        val data : Items = dataList[position]
        Log.d("Firebase viewholder data ",data.toString())
//        Log.d("Firebase viewholder data Image",data.image.toString())
//        Log.d("Firebase viewholder data best Time ", data.time!!["bestTime"].toString())

        val imgPath = data.uid.toString() +"/"+data.image!![0].toString()
        val imgRef : StorageReference = mStorageReference.child(imgPath)
        val rating : Int = data.rating!!["total"]!!.toInt() / data.rating!!["voters"]!!.toInt()

        holder.name.text = data.name
        holder.img.clipToOutline = true
        imgRef.downloadUrl.addOnSuccessListener { Uri ->
            Glide.with(context)
                .load(Uri.toString())
                .centerCrop()
                .into(holder.img)

        }

        holder.location.text = data.address!![1].toString()
        holder.rBar.rating = rating.toFloat()
        holder.ratingTitle.text = "($rating)"
        holder.itemView.setOnClickListener {
            val i : Intent = Intent(context, DetailActivity::class.java)
                i.putExtra("uid", data.uid)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}