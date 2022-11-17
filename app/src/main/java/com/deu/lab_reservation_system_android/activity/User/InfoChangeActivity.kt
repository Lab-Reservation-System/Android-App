package com.deu.lab_reservation_system_android.activity.User

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.nav.Assistant_Nav_Activity
import com.deu.lab_reservation_system_android.nav.Professor_Nav_Activity
import com.deu.lab_reservation_system_android.nav.Student_Nav_Activity
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 개인 정보 수정
class InfoChangeActivity : AppCompatActivity() {
    lateinit var data : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_change)


        data = intent.getSerializableExtra("key") as User

        val name = findViewById<EditText>(R.id.user_name)
        val id = findViewById<EditText>(R.id.user_Id)
        val pw = findViewById<EditText>(R.id.user_edit_pw)
        val pw_ch = findViewById<EditText>(R.id.user_edit_pw_ch)
        val email = findViewById<EditText>(R.id.user_edit_email)
        val phone = findViewById<EditText>(R.id.user_edit_phone)
        val back_btn = findViewById<Button>(R.id.back_page_btn)
        val save_btn = findViewById<Button>(R.id.info_save_btn)
        val delete_btn= findViewById<Button>(R.id.info_delete_btn)

        name.setText(data.name)
        id.setText(data.id)
        email.setText(data.email)
        phone.setText(data.phoneNumber)

        back_btn.setOnClickListener {
            job_check(data)
        }

        save_btn.setOnClickListener {

            if( pw.text.toString().length == 0 ||
                pw_ch.text.toString().length == 0 ||
                email.text.toString().length == 0 ||
                phone.text.toString().length == 0 ){

                val builder = AlertDialog.Builder(this)
                    .setTitle("모든 내용을 입력하십시오!")
                    .setMessage("다시 입력하세요")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener{ dialog, which ->  })
                builder.show()

            }

            else if(pw.text.toString() != pw_ch.text.toString()) {
                val builder = AlertDialog.Builder(this)
                    .setTitle("비밀번호가 일치하지 않습니다!")
                    .setMessage("다시 입력하세요")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener{ dialog, which ->   })
                builder.show()
            }


            else {
                data.password = pw.text.toString()
                data.email = email.text.toString()
                data.phoneNumber = phone.text.toString()
                save_edit()
            }
        }
        delete_btn.setOnClickListener {
            DeleteUser(data.id)
        }

    }

    fun DeleteUser(user_id: String) {
        val call = RetrofitBuilder.api_user.DeleteUserResponse(user_id)
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    try {
                        // 로그인으로 이동
                        alertDelte()



                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("RESPONSE", "FAILURE")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }


    fun save_edit() {
        val call = RetrofitBuilder.api_user.EditUserResponse(data.id,data)
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    try {
                            job_check(data)

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패

                    Log.d("RESPONSE", "FAILURE")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }

    fun job_check(user : User){
        var intent : Intent

        if(user.job == "student")
        {
            intent = Intent(this, Student_Nav_Activity::class.java)
            intent.putExtra("key",user)
            startActivity(intent)

        }
        else if(user.job == "admin") {
            Log.d("BUTTON CLICKED",user.job)
            intent = Intent(this, Assistant_Nav_Activity::class.java)
            intent.putExtra("key", user)
            startActivity(intent)
        }
        else if(user.job == "professor") {
            Log.d("BUTTON CLICKED", user.job)
            intent = Intent(this, Professor_Nav_Activity::class.java)
            intent.putExtra("key", user)
            startActivity(intent)

        }
    }

    fun alertDelte(){
        val builder = AlertDialog.Builder(this)
            .setTitle("회원 탈퇴 성공")
            .setMessage("로그인 화면으로 돌아갑니다.")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener{ dialog, which ->
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                })
        builder.show()


    }
}

