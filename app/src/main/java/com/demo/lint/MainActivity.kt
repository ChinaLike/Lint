package com.demo.lint

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.core.util.isNotNullOrEmpty

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("1", "2")


        Toast.makeText(this, "", Toast.LENGTH_LONG).show()

        Thread()

        checkSelfPermission()

        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCEPT_HANDOVER)

//        ActivityCompat.requestPermissions(this, listOf<String>().toTypedArray(), 0x12)
//       ActivityCompat.shouldShowRequestPermissionRationale()


        Color.parseColor("#EEE")
        Color.parseColor("#EEE")


        View(this).setOnClickListener {

        }

        AppCompatTextView(this).setOnClickListener {

        }

        resources.getDrawable(R.drawable.ic_launcher_background)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


        if (TextUtils.isEmpty("")) {

        }
        var a: String? = null
        if (!a.isNullOrEmpty()) {
            a.isNotEmpty()
        }
        if (a.isNotNullOrEmpty()) {
            a.isNotEmpty()
        }
    }

    //测试单行注释，是不是这样!
    override fun onClick(p0: View?) {
        //测试单行注释，是不是这样.
    }

    private fun checkSelfPermission() {

    }
}