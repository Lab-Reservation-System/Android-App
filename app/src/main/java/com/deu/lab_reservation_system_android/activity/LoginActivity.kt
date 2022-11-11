package com.deu.lab_reservation_system_android.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.Dto.LoginDto
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    var id: String = ""
    var pw: String = ""

    // 엑티비티가 생성되었을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃을 설정
        setContentView(R.layout.login)

        val login_btn = findViewById<Button>(R.id.login_btn)
        val regist_btn = findViewById<Button>(R.id.regist_btn)
        val userId = findViewById<EditText>(R.id.user_Id)
        val userPassword = findViewById<EditText>(R.id.user_Password)

        login_btn.setOnClickListener{
            id = userId.text.toString()
            pw = userPassword.text.toString()
            val user = LoginDto()
            user.id = userId.text.toString()
            user.password = userPassword.text.toString()

            Log.d("BUTTON CLICKED", "id: " + user.id + ", pw: " + user.password)
            login_success() //화면 전환 테스트
            //Login(user) 찐
        }

        regist_btn.setOnClickListener{  //회원 가입 버튼 클릭

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }
    }
    fun Login(user: LoginDto) {
        val call = RetrofitBuilder.api.getLoginResponse(user)

        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    login_success()
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

    fun login_success(){

        val intent = Intent(this, Access_TokenActivity::class.java)
        startActivity(intent)
    }

}
