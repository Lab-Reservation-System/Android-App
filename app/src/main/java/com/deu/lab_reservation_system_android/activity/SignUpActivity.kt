package com.deu.lab_reservation_system_android.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.Dto.LoginDto

class SignUpActivity : AppCompatActivity() {

    var name: String = ""
    var id: String = ""
    var password: String = ""
    var password_ch: String = ""
    var email: String = ""
    var phone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signup_btn = findViewById<Button>(R.id.signup_btn)
        val back_btn = findViewById<Button>(R.id.back_btn)
        val stu_name = findViewById<EditText>(R.id.name)
        val stu_Id = findViewById<EditText>(R.id.stu_id)
        val stu_password = findViewById<EditText>(R.id.password)
        val stu_password_ch = findViewById<EditText>(R.id.password_ch)
        val stu_email = findViewById<EditText>(R.id.email)
        val stu_phone = findViewById<EditText>(R.id.phone)


        signup_btn.setOnClickListener{
            name = stu_name.text.toString()
            id = stu_Id.text.toString()
            password = stu_password.text.toString()
            password_ch = stu_password_ch.text.toString()
            email = stu_email.text.toString()
            phone = stu_phone.text.toString()
            //val user = LoginDto()

            Log.d("BUTTON CLICKED", "pw: " + password + ", pwch: " + password_ch)

            if(password != password_ch) {
                val builder = AlertDialog.Builder(this)
                    .setTitle("비밀번호가 일치하지 않습니다!")
                    .setMessage("다시 입력하세요")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener{ dialog, which ->
                            Toast.makeText(this, "확인", Toast.LENGTH_SHORT).show()
                        })
                builder.show()
            }

        }

        back_btn.setOnClickListener{
            finish()
        }
    }
}