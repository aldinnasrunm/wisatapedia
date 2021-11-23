package com.example.wisatapedia.viewmodels

import com.google.firebase.firestore.GeoPoint


data class Items(
    var uid: String? = null,
    var image : ArrayList<String>? = null,
    var address : ArrayList<String>? = null,
    var price : String?  = null,
    var name : String? = null,
    var rating : HashMap<String, Int>? = null,
    var description : String? = null,
    var time: HashMap<String, String>? = null,
    var facilities : ArrayList<String>? = null,
    var longlang : GeoPoint? = null,
    var category : String? = null,
    var promoted : Boolean = false,

)
