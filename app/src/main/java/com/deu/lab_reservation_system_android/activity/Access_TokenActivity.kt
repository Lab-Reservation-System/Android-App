package com.deu.lab_reservation_system_android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.deu.lab_reservation_system_android.R

class Access_TokenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_token)

        val signup_btn = findViewById<Button>(R.id.useRequestBtn)
        val back_btn = findViewById<Button>(R.id.backPageBtn)

        signup_btn.setOnClickListener{

        }

        back_btn.setOnClickListener{
            finish()
        }

    }
}