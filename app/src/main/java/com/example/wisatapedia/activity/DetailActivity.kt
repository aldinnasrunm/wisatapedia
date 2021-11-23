package com.example.wisatapedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivityDetailBinding
import com.example.wisatapedia.viewmodels.Items
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

private lateinit var binding: ActivityDetailBinding
//private lateinit var uid : String
private lateinit var db : FirebaseFirestore
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseFirestore.getInstance()
        getIntentData()

    }

    private fun getIntentData() {
        val uid : String = intent.getStringExtra("uid").toString()
        getData(uid)
    }

    private fun getData(uid: String) {
        db.collection("item").document(uid)
            .get().addOnSuccessListener { result ->
                var x = result.toObject(Items::class.java)
                Log.d("Firebase Data Check ", x.toString())

            }.addOnFailureListener { err ->
                Log.e("Firebase error", "getData: ${err.localizedMessage} ")

            }

    }


}