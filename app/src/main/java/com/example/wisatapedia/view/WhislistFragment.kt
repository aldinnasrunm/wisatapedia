package com.example.wisatapedia.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivityDashBoardBinding.inflate
import com.example.wisatapedia.databinding.FragmentWhislistBinding
import com.example.wisatapedia.viewmodels.UserDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private lateinit var db : FirebaseFirestore
private lateinit var auth : FirebaseAuth
private lateinit var binding : FragmentWhislistBinding
class WhislistFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhislistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val currentUser = auth.currentUser
        db.collection("users").document(currentUser?.uid.toString()).get().addOnSuccessListener { doc ->
            var x = doc.toObject(UserDataModel::class.java)
//            Log.d("Check Data Whislist",  )

            }.addOnFailureListener {exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }

        }

    }
