package com.example.wisatapedia.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.FragmentSearchBinding
import com.example.wisatapedia.viewmodels.ItemAdapter
import com.example.wisatapedia.viewmodels.Items
import com.google.firebase.firestore.*

private lateinit var binding : FragmentSearchBinding
private lateinit var dataList :ArrayList<Items>
private lateinit var itemAdapter : ItemAdapter
private lateinit var db : FirebaseFirestore
class SearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvMain.layoutManager = LinearLayoutManager(this.context)
        binding.rvMain.setHasFixedSize(true)

        dataList = arrayListOf()
        itemAdapter = ItemAdapter(dataList, context)


        binding.rvMain.adapter = itemAdapter

        EventChangeListener()
//        binding.rvMain.item
    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("item").whereEqualTo("category", "mountain")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if(error != null){
                        Log.e("Firebase Error", error.message.toString())
                        return
                    }
                    Log.d("Firebase Data", value.toString())
                    for (dc : DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            Log.d("Doc error", dc.document.toString())
                            var x = dc.document.toObject(Items::class.java)
                            Log.d("Document Data : ", x.toString())
                            dataList.add(dc.document.toObject(Items::class.java))
                        }
                    }
                    itemAdapter.notifyDataSetChanged()
                }
            })
    }

//    fun fragTransaction() {
//        val viewFragment = ViewFragment()
//        val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
//        transaction.replace(R.id.fragment_main, viewFragment)
//        transaction.commit()
//
//    }

    fun changeView(){

    }


}
