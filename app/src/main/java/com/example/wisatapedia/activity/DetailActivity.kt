package com.example.wisatapedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivityDetailBinding
import com.example.wisatapedia.view.ViewFragment
import com.example.wisatapedia.viewmodels.FacilitiesAdapter
import com.example.wisatapedia.viewmodels.Items
import com.example.wisatapedia.viewmodels.SliderAdapterFirebase
import com.example.wisatapedia.viewmodels.SliderData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.smarteist.autoimageslider.SliderView
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


private lateinit var binding: ActivityDetailBinding
//private lateinit var uid : String
private lateinit var format: NumberFormat
lateinit var mStorageReference: StorageReference
private lateinit var latlong : String
private lateinit var db : FirebaseFirestore

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.hide()
        db = FirebaseFirestore.getInstance()
        format  = NumberFormat.getCurrencyInstance()
        format.setCurrency(Currency.getInstance("IDR"));
        mStorageReference = FirebaseStorage.getInstance().getReference()
        getIntentData()

//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

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
                binding.tvDetailName.text = x?.name.toString()
                binding.tvDetailAddress.text = x?.address!![0] +","+ x?.address!![1] +","+ x?.address!![2]
                var totalRating = x.rating!!["total"]!!.toInt() / x.rating!!["voters"]!!.toInt()
                binding.tvDetailRating.text = "$totalRating/5"
                binding.tvDetailDescription.text = x?.description.toString()
                latlong = x?.longlang?.latitude.toString() +","+ x?.longlang?.longitude.toString()
                //image data
                val imgPath1 = x.uid.toString() +"/"+ x.image!![0].toString()
                val imgPath2 = x.uid.toString() +"/"+ x.image!![1].toString()
                val imgPath3 = x.uid.toString() +"/"+ x.image!![2].toString()
//                var url1 : StorageReference = mStorageReference.child(imgPath1)
//                var url2 : StorageReference = mStorageReference.child(imgPath2)
//                var url3 : StorageReference = mStorageReference.child(imgPath3)
                var price = x?.price
                var open = x.time!!["open"].toString()
                var close = x.time!!["close"].toString()
                var bestTime = x.time!!["bestTime"].toString()
                var dayOn = x.time!!["dayOn"].toString()
                var dayOff = x.time!!["dayOff"].toString()
                //get facilites array
                var facilities : ArrayList<String>? = x.facilities


                setOpen(open, close, bestTime, dayOn, dayOff)
                setPrice(price.toString())
                if (facilities != null){
                    setFacilities(facilities)
                }else{
                    val recovery: ArrayList<String> = ArrayList()
                    recovery.add("parkir")
                    setFacilities(recovery)
                }
                setSlider(imgPath1, imgPath2, imgPath3)
                addFragment()
            }.addOnFailureListener { err ->
                Log.e("Firebase error", "getData: ${err.localizedMessage} ")

            }

    }

    private fun setFacilities(facilities: ArrayList<String>?) {
        binding.rvListFacilities.setHasFixedSize(true)
        var listAdapter = FacilitiesAdapter(facilities!!)
        binding.rvListFacilities.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvListFacilities.adapter = listAdapter
    }

    private fun setOpen(
        open: String,
        close: String,
        bestTime: String,
        dayOn: String,
        dayOff: String
    ) {
        if(dayOn == "all"){
            if (open == "all"){
                binding.tvDetailOpen.text = "Senin-Minggu\nSepanjang Hari"
            }
            binding.tvDetailOpen.text = "Senin-Minggu\n$open-$close"
        }else{
            if (open == "all"){
                binding.tvDetailOpen.text = "$dayOn-$dayOff\nSepanjang Hari"
            }
            binding.tvDetailOpen.text = "$dayOn-$dayOff\n$open-$close"
        }

        binding.tvDetailBestTime.text = bestTime
    }

    private fun setPrice(price: String) {
        if (price == "-"){
            binding.tvDetailTicket.text = "Gratis"
        }else{
            var intPrice = price.toInt()
            var idrFormat :String = format.format(intPrice).toString()
            binding.tvDetailTicket.text = idrFormat
        }

    }

    private fun setSlider(url1: String, url2: String, url3: String) {
//        var url1 = "https://github.com/namdoosan/beliingelato/blob/main/wisata1.jpg?raw=true"
//        var url2 = "https://github.com/namdoosan/beliingelato/blob/main/wisata2.jpg?raw=true"
//        var url3 = "https://github.com/namdoosan/beliingelato/blob/main/wisata3.jpg?raw=true"
        Log.d("setSlider: ",url1 )
        val sliderDataArrayList: ArrayList<SliderData> = ArrayList()
        val sliderView = binding.imageSliderDetail

        sliderDataArrayList.add(SliderData(url1))
        sliderDataArrayList.add(SliderData(url2))
        sliderDataArrayList.add(SliderData(url3))

        val adapter = SliderAdapterFirebase(this,  sliderDataArrayList)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }

    override fun onMapReady(p0: GoogleMap) {
//        mMap = p0
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun addFragment(){
        val mainFrag = R.id.fragmentMaps
        val bundle = Bundle()
        val fragment = ViewFragment()

        bundle.putString("coordinate", latlong)
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(mainFrag, fragment, fragment.javaClass.simpleName)
            .commit()
    }



}