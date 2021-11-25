package com.example.wisatapedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivityEmailConfirmationBinding
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding : ActivityEmailConfirmationBinding
private lateinit var auth : FirebaseAuth
class EmailConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        binding.btnSendVeritification.setOnClickListener {
            sendEmailVerifity()
        }
    }

    private fun sendEmailVerifity() {
        var currentUser = auth.currentUser
        currentUser?.sendEmailVerification()
        var i = Intent(this, LoginActivity::class.java)
        startActivity(i).also {
            this.finish()
        }
    }
}