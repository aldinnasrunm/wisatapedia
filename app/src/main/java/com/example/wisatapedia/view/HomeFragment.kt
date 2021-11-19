package com.example.wisatapedia.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.FragmentHomeBinding
import com.example.wisatapedia.viewmodels.SliderAdapter
import com.example.wisatapedia.viewmodels.SliderData
import com.smarteist.autoimageslider.SliderView

private lateinit var binding: FragmentHomeBinding
class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
    }
}