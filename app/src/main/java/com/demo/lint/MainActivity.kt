package com.demo.lint

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("", "")


        Toast.makeText(this, "", Toast.LENGTH_LONG).show()

        Thread()

        checkSelfPermission()

        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCEPT_HANDOVER)

//        ActivityCompat.requestPermissions(this, listOf<String>().toTypedArray(), 0x12)
//       ActivityCompat.shouldShowRequestPermissionRationale()


        try {
            Color.parseColor("#EEE")
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }


    }

    override fun onClick(p0: View?) {

    }

    private fun checkSelfPermission() {

    }
}