package com.example.olimpia_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_send_data.*

class SendDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_data)
    }

    fun sendData(view: View) {
        progress_circular.visibility = VISIBLE
        val timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                progress_circular.visibility = GONE
                Toast.makeText(this@SendDataActivity,"Datos enviados",Toast.LENGTH_SHORT).show()
            }
        }
        timer.start()
    }
}
