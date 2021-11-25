package com.example.wisatapedia.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wisatapedia.databinding.FragmentViewBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


private lateinit var coordinate: String
private lateinit var mMap: GoogleMap
private lateinit var mapView: MapView
private lateinit var binding : FragmentViewBinding
class ViewFragment : Fragment(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewBinding.inflate(layoutInflater)
        coordinate = arguments?.get("coordinate").toString()
//        var theView = inflater.inflate(R.layout.fragment_view, container, false)
//        mapView = theView.findViewById(R.id.mapView)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.map as MapView
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val mapFragment = childFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(context)
//        mapView.onCreate(savedInstanceState)
//        mapView.getMapAsync { mapboxMap: MapboxMap ->
//            mapboxMap.setStyle(
//                Style.Builder().fromUrl(Style.MAPBOX_STREETS)
//            )
//        }


    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        val coor = coordinate.split(",")
        var lat = coor[0].toDouble()
        var long = coor[1].toDouble()
        // Add a marker in Sydney and move the camera
        val position = LatLng(lat, long)
        mMap.addMarker(
            MarkerOptions()
            .position(position)
            .title("Destinasi"))
        mMap.setMaxZoomPreference(40.0f)
        mMap.setMinZoomPreference(6.0f)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 10.0f))

    }


//    override fun onStart() {
//        super.onStart()
//        mapView.onStart()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mapView.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mapView.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mapView.onStop()
//    }
}
