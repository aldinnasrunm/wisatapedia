package com.example.wisatapedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivityLoginBinding


private lateinit var binding: ActivityLoginBinding
class loginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}