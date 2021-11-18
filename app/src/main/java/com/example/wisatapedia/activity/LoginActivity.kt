package com.example.wisatapedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wisatapedia.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


private lateinit var binding: ActivityLoginBinding
private lateinit var auth : FirebaseAuth
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvLoginToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            taskIntent(intent)
        }
        binding.btnLogin.setOnClickListener {
            setLogin(getLoginDataEmail(), getLoginDataPassword())
        }
    }
    private fun getLoginDataEmail(): String {
        return binding.etUserEmail.text.toString()
    }
    private fun getLoginDataPassword() :String {
        return binding.etUserPassword.text.toString()
    }
    private fun setLogin(email : String, password : String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val intent = Intent(this, DashBoardActivity::class.java)
                taskIntent(intent)
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
        }

    }



    private fun taskIntent(intent: Intent) {
        startActivity(intent)
        finish()
    }


}