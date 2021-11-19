package com.example.wisatapedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding: ActivityRegisterBinding
private lateinit var auth : FirebaseAuth
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth  = FirebaseAuth.getInstance()

//        binding.btnGoLogin.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            taskIntent(intent)
//        }

        binding.btnRegister.setOnClickListener {
            if (getRegisterDataPassword() != null){
                setRegister(getRegisterDataEmail(), getRegisterDataPassword())
            }else{
                binding.etUserPasswordCheckRegister.setError("Upps, Check Password kembali")
            }
        }
    }

    private fun getRegisterDataEmail() : String{
        return binding.etUserEmailRegister.text.toString()
    }

    private fun getRegisterDataPassword(): String? {
        val password = binding.etUserPasswordRegister.text.toString()
        val checkPassword = binding.etUserPasswordCheckRegister.text.toString()
        if (password == checkPassword){
            return password
        }else{
            return null
        }

    }

    private fun setRegister(email : String, password : String?){
        if (password != null) {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val intent = Intent(this, DashBoardActivity::class.java)
                    taskIntent(intent)
                }
            }.addOnFailureListener { exc ->
                Toast.makeText(this, exc.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Mohon maaf silahkan coba kembali sekali lagi :(", Toast.LENGTH_SHORT).show()
        }
    }




    private fun taskIntent(intent : Intent){
        startActivity(intent)
        finish()
    }

}