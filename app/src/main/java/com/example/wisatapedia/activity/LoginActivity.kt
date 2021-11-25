package com.example.wisatapedia.activity

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.wisatapedia.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private lateinit var binding: ActivityLoginBinding
private lateinit var auth : FirebaseAuth
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.tvSendAgainVeritification.visibility = View.GONE
        binding.tvSendAgainVeritification.setOnClickListener {
            veritifyEmail()
        }
        binding.tvLoginToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            taskIntent(intent)
        }
        binding.btnLogin.setOnClickListener {
            setLogin(getLoginDataEmail(), getLoginDataPassword())
        }
    }

    private fun veritifyEmail() {
        var currentUser  = FirebaseAuth.getInstance().currentUser
        currentUser?.sendEmailVerification()
        Toast.makeText(this, "Email Veritifikasi telah dikirim", Toast.LENGTH_SHORT).show()
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
                var currentUser  = FirebaseAuth.getInstance().currentUser
                if (currentUser != null){
                    if (currentUser.isEmailVerified){
                        val intent = Intent(this, DashBoardActivity::class.java)
                        taskIntent(intent)
                    }else{
                        binding.tvSendAgainVeritification.visibility = View.VISIBLE

                        Toast.makeText(this, "Cek Email anda untuk veritifikasi kemudian login kembali", Toast.LENGTH_SHORT).show()
                    }
                }

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