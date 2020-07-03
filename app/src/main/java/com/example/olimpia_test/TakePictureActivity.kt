package com.example.olimpia_test

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import kotlinx.android.synthetic.main.activity_take_picture.*

class TakePictureActivity : AppCompatActivity() {

    val REQUEST_CODE = 200
    var pictureTake = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            pictureTake = true
            takePicture.error = null
            showPicture.setImageBitmap(data.extras?.get("data") as Bitmap)
        }
    }

    fun takePicture(view: View) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }

    fun startNextActivity(view: View) {
        when(pictureTake){
            true->{
                startActivity(Intent(this, LocationActivity::class.java))
                finishAffinity()
            }
            else->{
                takePicture.error = getString(R.string.take_picture)
            }

        }

    }


}
