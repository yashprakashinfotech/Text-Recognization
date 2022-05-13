package com.example.textrecognization

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText

class ScanActivity : AppCompatActivity() {

    private lateinit var btnSnap : Button
    private lateinit var btnDetect : Button
    private lateinit var txtDetect : TextView
    private lateinit var imgDetectImage : ImageView

    private var imageBitmap : Bitmap? =null

    private val requestImageCapture = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        initView()

        btnDetect.setOnClickListener {
            detectText()
        }
        btnSnap.setOnClickListener {
            takePictureCamera()
        }

    }

    private fun initView(){
        btnSnap = findViewById(R.id.btnSnap)
        btnDetect = findViewById(R.id.btnDetect)

        txtDetect = findViewById(R.id.txtDetect)
        imgDetectImage = findViewById(R.id.imgDetectImage)
    }
    @SuppressLint("QueryPermissionsNeeded")
    private fun takePictureCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent,requestImageCapture)
//        if (takePictureIntent.resolveActivity(packageManager)!=null){
//            startActivityForResult(takePictureIntent,requestImageCapture)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == requestImageCapture && resultCode == Activity.RESULT_OK){
            val extras = data!!.extras
            imageBitmap = extras!!.get("data") as Bitmap
            imgDetectImage.setImageBitmap(imageBitmap)
        }

    }

    private fun detectText() {

        val image = FirebaseVisionImage.fromBitmap(imageBitmap!!)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
        detector.processImage(image).addOnSuccessListener(OnSuccessListener<FirebaseVisionText> {
            firebaseVisionText ->
            processText(firebaseVisionText)
        }).addOnFailureListener(OnFailureListener {
            Toast.makeText(this,"Failure To the detect text",Toast.LENGTH_SHORT).show()
        })

    }

    private fun processText(text: FirebaseVisionText){
        val blocks = text.textBlocks
        if (blocks.size == 0){
            Toast.makeText(this,"No text Found",Toast.LENGTH_SHORT).show()
        }
        else{
            for (block in text.textBlocks){
                val txt = block.text
                txtDetect.text = txt
            }
        }
    }

}