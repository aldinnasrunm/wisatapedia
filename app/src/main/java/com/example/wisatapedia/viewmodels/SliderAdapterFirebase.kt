package com.example.wisatapedia.viewmodels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.wisatapedia.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.smarteist.autoimageslider.SliderViewAdapter
import java.util.ArrayList

lateinit var mStorageReference: StorageReference
class SliderAdapterFirebase(context: Context?, sliderDataArrayList: ArrayList<SliderData>) :
    SliderViewAdapter<SliderAdapterFirebase.SliderAdapterViewHolder>() {
    // list for storing urls of images.
    private val mSliderItems: List<SliderData>
    var crx = context
    // We are inflating the slider_layout
    // inside on Create View Holder method.
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, null)
        return SliderAdapterViewHolder(inflate)
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    override fun onBindViewHolder(viewHolder: SliderAdapterViewHolder, position: Int) {
        val sliderItem = mSliderItems[position]
        mStorageReference = FirebaseStorage.getInstance().getReference()

        // Glide is use to load image
        // from url in your imageview.
        val imgRef : StorageReference = mStorageReference.child(sliderItem.imgUrl)
        imgRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(crx!!)
                .load(uri.toString())
                .centerCrop()
                .into(viewHolder.imageViewBackground)
        }

    }

    // this method will return
    // the count of our list.
    override fun getCount(): Int {
        return mSliderItems.size
    }

    class SliderAdapterViewHolder(itemView: View) :
        ViewHolder(itemView) {
        // Adapter class for initializing
        // the views of our slider view.
        lateinit var atemView: View
        var imageViewBackground: ImageView

        init {
            imageViewBackground = itemView.findViewById(R.id.myimage)
            this.atemView = itemView
        }
    }

    // Constructor
    init {
        mSliderItems = sliderDataArrayList
    }
}
