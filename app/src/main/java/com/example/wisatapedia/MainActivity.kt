package com.example.wisatapedia

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.wisatapedia.activity.loginActivity
import com.example.wisatapedia.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding: ActivityMainBinding
private lateinit var auth : FirebaseAuth
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        Log.d(TAG, "onStart: $currentUser ")
        Toast.makeText(this, currentUser.toString(), Toast.LENGTH_SHORT).show()
        if (currentUser == null){
            val intent = Intent(this, loginActivity::class.java)
        }
    }
}