package com.example.textrecognization

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnCaptureImage : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCaptureImage = findViewById(R.id.btnCaptureImage)

        btnCaptureImage.setOnClickListener {
            val i = Intent(this,ScanActivity::class.java)
            startActivity(i)
        }
    }
}