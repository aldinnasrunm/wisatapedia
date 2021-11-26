package com.example.wisatapedia.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wisatapedia.R
import com.example.wisatapedia.activity.DetailActivity
import com.example.wisatapedia.view.WhislistFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class WhistListItemAdapter(cxt: Context, private var dataList: ArrayList<String>):RecyclerView.Adapter<WhistListItemAdapter.MyViewHolder>() {

    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var mStorageReference : StorageReference = FirebaseStorage.getInstance().getReference()
    private var context = cxt

    private var onChange = WhislistFragment()

    public interface onItemClickListener{

    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var name : TextView = itemView.findViewById(R.id.tvItemNameWhislist)
        var location : TextView = itemView.findViewById(R.id.tvItemLocationWhislist)
        var img : ImageView = itemView.findViewById(R.id.ivTumbnailWhislist)
        var rBar : RatingBar = itemView.findViewById(R.id.rbarItemWhislist)
        var ratingTitle : TextView = itemView.findViewById(R.id.tvRatingWhislist)
        var delete: ImageView = itemView.findViewById(R.id.ivIcDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_whislist, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var data = dataList[position]
        db.collection("item").document(data.toString()).get().addOnSuccessListener { document ->
            var doc = document?.toObject(Items::class.java)
            holder.name.text = doc?.name
            holder.location.text = doc?.address!![2].toString()
            //get Image
            val imgPath = doc.uid.toString() +"/"+ doc.image!![0]
            val imgRef : StorageReference = mStorageReference.child(imgPath)

            //get Rating
            val rating : Int = doc.rating!!["total"]!!.toInt() / doc.rating!!["voters"]!!.toInt()

            //set Image
            holder.img.clipToOutline = true
            imgRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri.toString())
                    .centerCrop()
                    .into(holder.img)
            }
            //set Rating
            holder.rBar.rating = rating.toFloat()
            holder.ratingTitle.text = "($rating)"

            //onclick
            holder.itemView.setOnClickListener {
                val i : Intent = Intent(context, DetailActivity::class.java)
                i.putExtra("uid", doc.uid)
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(i)
            }

        }

        //delete
        holder.delete.setOnClickListener {
            var currentUser = auth.currentUser
            Log.d("Delete item ", data.toString())
            db.collection("users").document(currentUser!!.uid.toString()).update("destination", FieldValue.arrayRemove(data.toString()))
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}