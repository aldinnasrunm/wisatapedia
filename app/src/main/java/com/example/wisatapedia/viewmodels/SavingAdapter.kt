package com.example.wisatapedia.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wisatapedia.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

private lateinit var format: NumberFormat
private lateinit var db : FirebaseFirestore
class SavingAdapter(private var dataSet : ArrayList<SavingData>, private var uid :String): RecyclerView.Adapter<SavingAdapter.MyViewHolder>() {



    class MyViewHolder (v : View) : RecyclerView.ViewHolder(v){
        var mTotal : TextView = v.findViewById(R.id.tvTotal)
        var mEstimated : TextView = v.findViewById(R.id.tvEstiamted)
        var mTab : TextView = v.findViewById(R.id.tvTotalTabungan)
        var mTarget : TextView = v.findViewById(R.id.tvTargetTabungan)
        var mAdd : ImageView = v.findViewById(R.id.ivAddTabungan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_saving, parent, false)
        return SavingAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       var data : SavingData = dataSet[position]
        format  = NumberFormat.getCurrencyInstance()
        format.setCurrency(Currency.getInstance("IDR"))
        db = FirebaseFirestore.getInstance()
        var tabungan = data.target!!.toInt() * data.estimated
        holder.mTotal.text = format.format(data.total!!.toFloat())
        holder.mEstimated.text = data.estimated.toString()
        holder.mTab.text = format.format(tabungan.toFloat())
        holder.mTarget.text = format.format(data.target!!.toFloat())

        //add listener
        holder.mAdd.setOnClickListener {
            db.collection("users").document(uid).update("estimated", FieldValue.increment(1))

        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}