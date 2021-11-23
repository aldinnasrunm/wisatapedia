package com.example.wisatapedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivityDashBoardBinding
import com.example.wisatapedia.view.*
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.firebase.firestore.FirebaseFirestore

private lateinit var binding : ActivityDashBoardBinding
private lateinit var db : FirebaseFirestore
class DashBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
//        db = FirebaseFirestore.getInstance()
        setView()
    }


    private fun setView() {
        val MainFragment = HomeFragment()
        addFragment(MainFragment)
        val navbar = binding.bottomNavigationViewMain
        navbar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val fragment = HomeFragment()
                    addFragment(fragment)
                    return@setOnItemSelectedListener true
                }
                R.id.search -> {
                    val fragment = SearchFragment()
                    addFragment(fragment)
                    return@setOnItemSelectedListener true
                }
                R.id.whislist -> {
                    val fragment = WhislistFragment()
                    addFragment(fragment)
                    return@setOnItemSelectedListener true
                }
                R.id.setting -> {
                    val fragment = SettingFragment()
                    addFragment(fragment)
                    return@setOnItemSelectedListener true
                }

                else -> false
            }
        }
    }

    fun addFragment(fragment : Fragment){
        val mainFrag = R.id.fragment_main
        supportFragmentManager
            .beginTransaction()
            .replace(mainFrag, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    fun addFragmentView(data : String){
        Log.d("Transaction Fragment Data", data)
        val mainFrag = R.id.fragment_main
        supportFragmentManager
            .beginTransaction()
            .replace(mainFrag, ViewFragment(), ViewFragment().javaClass.simpleName)
            .commit()
    }

}