package com.deu.lab_reservation_system_android.activity.User

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.activity.student.model.Dto.SignupDto
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


            if(name.length == 0 || id.length ==0 || password.length == 0 || password_ch.length == 0 || email.length == 0|| phone.length == 0){
                val builder = AlertDialog.Builder(this)
                    .setTitle("모든 내용을 입력하십시오!")
                    .setMessage("다시 입력하세요")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener{ dialog, which ->
                            Toast.makeText(this, "확인", Toast.LENGTH_SHORT).show()
                        })
                builder.show()

            }



            else if(password != password_ch) {
                val builder = AlertDialog.Builder(this)
                    .setTitle("비밀번호가 일치하지 않습니다!")
                    .setMessage("다시 입력하세요")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener{ dialog, which ->
                            Toast.makeText(this, "확인", Toast.LENGTH_SHORT).show()
                        })
                builder.show()
            }

            else {
                val signupdto = SignupDto(id, name, password, phone, email, "student", false)
                Log.d("BUTTON CLICKED", "회원가입 버튼 클릭")
                SignUp(signupdto)
            }

        }

        back_btn.setOnClickListener{
            finish()
        }
    }

    fun SignUp(signupDto: SignupDto) {
        val call = RetrofitBuilder.api_user.getSignupResponse(signupDto)
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    try {
                        //어떻게 응답
//                        val email = JSONObject(response.body()).getString("email")
                        Log.d("RESPONSE: ", email)
                        finish()
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

}

