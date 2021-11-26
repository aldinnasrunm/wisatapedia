package com.example.wisatapedia.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisatapedia.R
import com.example.wisatapedia.activity.DetailActivity
import com.example.wisatapedia.activity.SavingActivity
import com.example.wisatapedia.databinding.FragmentHomeBinding
import com.example.wisatapedia.viewmodels.ItemAdapter
import com.example.wisatapedia.viewmodels.Items
import com.example.wisatapedia.viewmodels.SliderAdapter
import com.example.wisatapedia.viewmodels.SliderData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.smarteist.autoimageslider.SliderView

private lateinit var binding: FragmentHomeBinding
private lateinit var dataList :ArrayList<Items>
private lateinit var itemAdapter : ItemAdapter
private lateinit var db : FirebaseFirestore
private lateinit var auth : FirebaseAuth
class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvHome.layoutManager = LinearLayoutManager(this.context)
        binding.rvHome.setHasFixedSize(true)

        auth = FirebaseAuth.getInstance()
        var currenUser = auth.currentUser
        binding.tvTextGreetingName.text = currenUser?.email.toString()

        dataList = arrayListOf()
        itemAdapter = ItemAdapter(dataList, context)

        binding.rvHome.adapter = itemAdapter
        var url1 = "https://github.com/namdoosan/beliingelato/blob/main/wisata1.jpg?raw=true"
        var url2 = "https://github.com/namdoosan/beliingelato/blob/main/wisata2.jpg?raw=true"
        var url3 = "https://github.com/namdoosan/beliingelato/blob/main/wisata3.jpg?raw=true"

        val sliderDataArrayList: ArrayList<SliderData> = ArrayList()
        val sliderView = binding.slider

        // adding the urls inside array list
        // adding the urls inside array list
        sliderDataArrayList.add(SliderData(url1))
        sliderDataArrayList.add(SliderData(url2))
        sliderDataArrayList.add(SliderData(url3))

        // passing this array list inside our adapter class.

        // passing this array list inside our adapter class.
        val adapter = SliderAdapter(this.context, sliderDataArrayList)

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        binding.ivSaving.setOnClickListener {
            val i : Intent = Intent(context, SavingActivity::class.java)
            context?.startActivity(i)
        }

        EventChangeListenerLimited()
    }


    private fun EventChangeListenerLimited() {
        db = FirebaseFirestore.getInstance()
        db.collection("item").whereEqualTo("promoted", true).limit(5)
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