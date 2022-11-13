package com.deu.lab_reservation_system_android.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.activity.nav.Student_Nav_Activity

class Access_TokenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_token)

        val signup_btn = findViewById<Button>(R.id.useRequestBtn)
        val back_btn = findViewById<Button>(R.id.backPageBtn)

        signup_btn.setOnClickListener{
            val intent = Intent(this, Student_Nav_Activity::class.java)
            startActivity(intent)
        }

        back_btn.setOnClickListener{
            finish()
        }

    }
}