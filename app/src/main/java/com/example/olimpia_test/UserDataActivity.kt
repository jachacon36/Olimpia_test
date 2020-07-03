package com.example.olimpia_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_user_data.*

class UserDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)
    }

    fun startNextActivity(view: View) {
        when(validateFields()){
            true->{
                startActivity(Intent(this, TakePictureActivity::class.java))
                finishAffinity()
            }
            else->{
                return
            }
        }

    }

    private fun validateFields(): Boolean{
        when{
            name.text.isEmpty()->{
                name.error = getString(R.string.empty)
                return false
            }
            document.text.isEmpty()->{
                document.error = getString(R.string.empty)
                return false

            }
            address.text.isEmpty()->{
                address.error = getString(R.string.empty)
                return false
            }
            city.text.isEmpty()->{
                city.error = getString(R.string.empty)
                return false
            }
            country.text.isEmpty()->{
                country.error = getString(R.string.empty)
                return false
            }
            phone.text.isEmpty()->{
                phone.error = getString(R.string.empty)
                return false
            }
        }
        return true
    }
}
