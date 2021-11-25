package com.example.wisatapedia.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wisatapedia.R
import com.example.wisatapedia.activity.LoginActivity
import com.example.wisatapedia.databinding.FragmentSettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private lateinit var binding: FragmentSettingBinding
private lateinit var auth : FirebaseAuth
class SettingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }


    private fun logout(){
        Firebase.auth.signOut()
        val i = Intent(context, LoginActivity::class.java)
        this.activity?.finish().also {
            context?.startActivity(i)
        }
    }
}