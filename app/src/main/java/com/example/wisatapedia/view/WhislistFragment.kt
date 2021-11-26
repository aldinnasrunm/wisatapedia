package com.example.wisatapedia.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisatapedia.databinding.FragmentWhislistBinding
import com.example.wisatapedia.viewmodels.ClickData
import com.example.wisatapedia.viewmodels.UserDataModel
import com.example.wisatapedia.viewmodels.WhistListItemAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private lateinit var db: FirebaseFirestore
private lateinit var auth: FirebaseAuth
private lateinit var binding: FragmentWhislistBinding
private lateinit var arrayDestination: ArrayList<String>

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
        var clickData = ClickData()
        clickData.click = false
        if (clickData.click == false){
            setView()
            clickData.click = true
        }


    }

    fun setView() {
        val currentUser = auth.currentUser
        db.collection("users").document(currentUser?.uid.toString()).addSnapshotListener { doc, error ->
                var x = doc?.toObject(UserDataModel::class.java)
                if (x?.destination != null) {
                    var arrayData : ArrayList<String> = ArrayList()
                    for (item in x.destination!!) {

//                        arrayDestination.add(item)
                        arrayData.add(item)
                    }
                    setRecyclerView(arrayData)
                }

//            Log.d("Check Data Whislist",  )

            }
    }

    private fun setRecyclerView(arrayDestination: ArrayList<String>) {
        var rvWhistlist =  binding.rvWhislist
        var adapter  = WhistListItemAdapter(requireActivity().applicationContext, arrayDestination)
        rvWhistlist.layoutManager = LinearLayoutManager(this.context)
        rvWhistlist.setHasFixedSize(true)
        rvWhistlist.adapter = adapter


    }


}
