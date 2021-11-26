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

        EventChangeListenerLimited()
        setClickButton()



//        binding.rvMain.item
    }

    private fun setClickButton() {
        //mountain
        binding.btnCategoryMountain.setOnClickListener {
            EventChangeCategory("mountain")
        }

        //beach
        binding.btnCategoryBeach.setOnClickListener {
            EventChangeCategory("beach")
        }

        //park
        binding.btnCategoryPark.setOnClickListener {
            EventChangeCategory("park")
        }

        //religious
        binding.btnCategoryReligious.setOnClickListener {
            EventChangeCategory("religius")
        }

        //others
        binding.btnCategoryOthers.setOnClickListener {
            EventChangeCategory("others")
        }

        //all
        binding.btnCategoryAll.setOnClickListener {
            EventChangeListener()
        }

        binding.btnSearch.setOnClickListener {
            var searchQuery = binding.etSearch.text.toString().lowercase()
            EventChangeSearch(searchQuery)

        }
    }

    private fun EventChangeListenerLimited() {
        db = FirebaseFirestore.getInstance()
        db.collection("item").limit(15)
            .get().addOnSuccessListener { value ->
                Log.d("Firebase Data", value.toString())
                dataList.clear()
                for (dc : DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
//                            Log.d("Doc error", dc.document.toString())
//                            var x = dc.document.toObject(Items::class.java)
//                            Log.d("Document Data : ", x.toString())
                        dataList.add(dc.document.toObject(Items::class.java))
                    }
                }
                itemAdapter.notifyDataSetChanged()
            }
    }
}


    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("item")
            .get().addOnSuccessListener { value ->
                    Log.d("Firebase Data", value.toString())
                    dataList.clear()
                    for (dc : DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
//                            Log.d("Doc error", dc.document.toString())
//                            var x = dc.document.toObject(Items::class.java)
//                            Log.d("Document Data : ", x.toString())
                            dataList.add(dc.document.toObject(Items::class.java))
                        }
                    }
                    itemAdapter.notifyDataSetChanged()
                }
            }


    private fun EventChangeCategory(category:String) {
        db = FirebaseFirestore.getInstance()
        db.collection("item").whereEqualTo("category", category)
            .get().addOnSuccessListener { value ->
                    Log.d("Firebase Data", value.toString())
                    dataList.clear()
                    for (dc : DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
//                            Log.d("Doc error", dc.document.toString())
//                            var x = dc.document.toObject(Items::class.java)
//                            Log.d("Document Data : ", x.toString())
                            dataList.add(dc.document.toObject(Items::class.java))
                        }
                    }
                    itemAdapter.notifyDataSetChanged()
                }

    }

    private fun EventChangeSearch(query:String) {
        db = FirebaseFirestore.getInstance()
        db.collection("item")
            .get().addOnSuccessListener { value ->
                Log.d("Firebase Data", value.toString())
                dataList.clear()
                for (dc : DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        var dataSet = dc.document.toObject(Items::class.java)
                        if (dataSet.name.toString().lowercase().contains(query)){
                        dataList.add(dataSet)
                    }
                }
                itemAdapter.notifyDataSetChanged()
            }
    }

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



