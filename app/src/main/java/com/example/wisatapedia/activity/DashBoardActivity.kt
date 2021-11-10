package com.example.wisatapedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivityDashBoardBinding

private lateinit var binding : ActivityDashBoardBinding
class DashBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
    }

    private fun setView(){
        binding.tvWelcome.text = "Hello"
    }
}