package com.deu.lab_reservation_system_android.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.activity.student.model.User

class Password_CheckActivity : AppCompatActivity() {

    lateinit var data: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_check)

        data = intent.getSerializableExtra("key") as User
        val use_btn = findViewById<Button>(R.id.useBtn)
        val back_btn = findViewById<Button>(R.id.backBtn)
        var input_pass = findViewById<EditText>(R.id.inputPassword)

        use_btn.setOnClickListener {
            Log.d("비밀번호 확인", data.password)
            if(data.password == input_pass.text.toString()) {

                intent = Intent(this, InfoChangeActivity::class.java)
                intent.putExtra("key",data)
                startActivity(intent)
            }
            //else { wrong() }

        }

        back_btn.setOnClickListener {
            finish()
        }



    }
}