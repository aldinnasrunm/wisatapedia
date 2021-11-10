package com.example.wisatapedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivityRegisterBinding

private lateinit var binding: ActivityRegisterBinding
class registerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}