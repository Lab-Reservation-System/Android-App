package com.deu.lab_reservation_system_android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.User

class Reservation_Activity : AppCompatActivity() {

    lateinit var data: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        data = intent.getSerializableExtra("key") as User



        val checkBox = arrayOfNulls<Button>(32)
        for(i in 0..31){
            checkBox[i] = findViewById(getResources().getIdentifier("check"+i, "id", "com.deu.lab_reservation_system_android.activity"));


        }


        val select_btn = findViewById<Button>(R.id.select_btn)
        select_btn.setOnClickListener {
            finish()
        }




        val back_btn = findViewById<Button>(R.id.back_btn)
        back_btn.setOnClickListener {
            finish()
        }
    }
}