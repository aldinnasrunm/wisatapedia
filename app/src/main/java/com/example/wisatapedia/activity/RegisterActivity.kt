package com.example.wisatapedia.activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivityRegisterBinding
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private lateinit var binding: ActivityRegisterBinding
private lateinit var auth : FirebaseAuth
private lateinit var actionCodeSettings : ActionCodeSettings
private lateinit var db : FirebaseFirestore
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        db = FirebaseFirestore.getInstance()
        auth  = FirebaseAuth.getInstance()

//        binding.btnGoLogin.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            taskIntent(intent)
//        }

            actionCodeSettings = actionCodeSettings {
            // URL you want to redirect back to. The domain (www.example.com) for this
            // URL must be whitelisted in the Firebase Console.
            url = "https://www.example.com/finishSignUp?cartId=1234"
            // This must be true
            handleCodeInApp = true
            setIOSBundleId("com.example.ios")
            setAndroidPackageName(
                "com.example.android",
                true, /* installIfNotAvailable */
                "12" /* minimumVersion */)
        }


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
                    var currentUser  = FirebaseAuth.getInstance().currentUser
                    setNewUserData(currentUser?.uid.toString(), currentUser?.email.toString())
                    if (currentUser != null) {
                        if (currentUser.isEmailVerified){
                            val intent = Intent(this, DashBoardActivity::class.java)
                            taskIntent(intent)
                        }else{
                            currentUser.sendEmailVerification()
                            Firebase.auth.signOut()
                            Toast.makeText(this, "Chek Email Anda Untuk Veritifikasi Kemudian Login Kembali", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            taskIntent(intent)
                        }
                    }

//                    Firebase.auth.sendSignInLinkToEmail(getRegisterDataEmail(), actionCodeSettings)
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                Log.d(TAG, "Email sent.")
//                                val intent = Intent(this, DashBoardActivity::class.java)
//                                taskIntent(intent)
//                            }
//                        }

                }
            }.addOnFailureListener { exc ->
                Toast.makeText(this, exc.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Mohon maaf silahkan coba kembali sekali lagi :(", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setNewUserData(uid : String, email : String){
        var arr : ArrayList<String> = ArrayList()
        var dataUser : Map<String, String> = mapOf("email" to email,
            "total" to "0",
            "target" to "0",
            "bulan" to "0",
            "estimate" to "0",
            "note" to "0")
        db.collection("users").document(uid).set(dataUser)
    }


    private fun taskIntent(intent : Intent){
        startActivity(intent)
        finish()
    }

}