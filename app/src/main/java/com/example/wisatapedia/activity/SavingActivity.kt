package com.example.wisatapedia.activity

import android.content.ContentValues.TAG
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.wisatapedia.R
import com.example.wisatapedia.databinding.ActivitySavingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

private lateinit var db : FirebaseFirestore
private lateinit var auth : FirebaseAuth
private lateinit var binding : ActivitySavingBinding
private lateinit var format: NumberFormat
class SavingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        format  = NumberFormat.getCurrencyInstance()
        format.setCurrency(Currency.getInstance("IDR"));

//        setView()
        setData()
        setButton()


    }

    private fun setData(){
        var currenUser = auth.currentUser
        db.collection("users").document(currenUser!!.uid.toString()).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: ${snapshot["saving"]}")
                var x = snapshot["saving"]
                Log.d("currenr data",x.toString() )
                setView(snapshot)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }



    private fun setView(snapshot: DocumentSnapshot) {
        var target = snapshot["target"].toString().toFloat()
        var total = snapshot["total"].toString().toFloat()
        var estimate = snapshot["estimate"].toString().toInt()
        var jumlahUang =target*estimate
        var sisaCicilan = total - jumlahUang

        binding.tvTarget.text = format.format(total)
        binding.tvJumlah.text = format.format(target)
        binding.tvTotal.text = format.format(jumlahUang.toFloat())
        binding.tvCicil.text = format.format(sisaCicilan)
        binding.tvNote.text = snapshot["note"].toString()

        if(sisaCicilan.toString() == "0"){
            completeCicilan()
        }
//        var rv = binding.rvSavingList
//        rv.setHasFixedSize(true)
//        rv.s
    }


    private fun setButton(){
        binding.btnAddSaving.setOnClickListener{
            alertAddSaving()
        }
        binding.btnPay.setOnClickListener {
            addCicilan()
        }
    }


    private fun completeCicilan(){
        AlertDialog.Builder(this)
            .setTitle("Cicilan Selesai")
            .setMessage("Selamat, kamu telah menyelesaikan cicilan kamu, sekarang saatnya kamu berlibur")
            .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                resetCicilan()
//                updateList()
            })
            .setCancelable(false)
            .show()
    }

    private fun resetCicilan(){
        var dataSet : HashMap<String, String> = hashMapOf(
            "total" to "0",
            "target" to "0",
            "bulan" to "0",
            "estimate" to "0",
            "note" to "0"
        )

        var currenUser = auth.currentUser
        db.collection("users").document(currenUser!!.uid).set(dataSet, SetOptions.merge())

    }

    private fun addCicilan() {
        var currenUser = auth.currentUser
        AlertDialog.Builder(this)
            .setTitle("Bayar Cicilan")
            .setMessage("Konfirmasi bahwa anda telah menyisihkan uang untuk ditabung")
            .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                db.collection("users").document(currenUser!!.uid.toString()).update("estimate", FieldValue.increment(1)).addOnSuccessListener {
                    Toast.makeText(this, "Suskses menambahkan tabungan", Toast.LENGTH_SHORT).show()
                    setData()
                }
//                updateList()
            })
            .setNegativeButton("tidak", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .setCancelable(false)
            .show()
    }



    private fun alertAddSaving(){
        var addView = LayoutInflater.from(this).inflate(R.layout.layout_add_saving, null, false)
        var mTotal = addView.findViewById<EditText>(R.id.etLayoutAddSavingTotal)
        var mBulan = addView.findViewById<EditText>(R.id.etLayoutAddSavingMoth)
        var mNote = addView.findViewById<EditText>(R.id.etLayoutAddSavingNote)

        AlertDialog.Builder(this)
            .setView(addView)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    var Total = mTotal.text.toString().toInt()
                    var Bulan = mBulan.text.toString().toInt()
                    var Note = mNote.text.toString()
                setData(Total,Bulan,Note)

//                updateList()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .setCancelable(false)
            .show()
    }

    private fun setData(Total: Int, Bulan: Int, Note: String) {
        var target = Total/Bulan
        var dataSet : HashMap<String, String> = hashMapOf(
            "total" to Total.toString(),
            "target" to target.toString(),
            "bulan" to Bulan.toString(),
            "estimate" to "0",
            "note" to Note
            )
        var uploadData : HashMap<String, HashMap<String, String>> = hashMapOf(
            "saving" to dataSet
        )

        var currenUser = auth.currentUser
        db.collection("users").document(currenUser!!.uid).set(dataSet, SetOptions.merge())

//        db.collection("users").document(currenUser!!.uid).update("saving", FieldValue.arrayUnion(dataSet)).addOnSuccessListener { v ->
////            Log.d("setData: ", v.toString())
//        }
    }

}