package com.deu.lab_reservation_system_android.activity.User

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.nav.Assistant_Nav_Activity
import com.deu.lab_reservation_system_android.nav.Professor_Nav_Activity
import com.deu.lab_reservation_system_android.nav.Student_Nav_Activity
import com.deu.lab_reservation_system_android.model.Dto.LoginDto
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import org.json.JSONObject
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
            val logindto = LoginDto()
            logindto.id = userId.text.toString()
            logindto.password = userPassword.text.toString()

            Log.d("BUTTON CLICKED", "id: " + logindto.id + ", pw: " + logindto.password)

            //var user2 = User("김준","1234","20183140", "1234-7897-44", true, "admin","asds@naver.com")
            //job_check(user2) //화면 전환 테스트
            Login(logindto) //찐
        }

        regist_btn.setOnClickListener{  //회원 가입 버튼 클릭

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }
    }
    fun Login(logindto: LoginDto) {
        val call = RetrofitBuilder.api_user.getLoginResponse(logindto)
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    try {
                        val name = JSONObject(response.body()).getString("name")
                        val id = JSONObject(response.body()).getString("id")
                        val password = JSONObject(response.body()).getString("password")
                        val permissionState = JSONObject(response.body()).getBoolean("permissionState")
                        var phoneNumber: String = JSONObject(response.body()).getString("phoneNumber")
                        var job = JSONObject(response.body()).getString("job")
                        var email = JSONObject(response.body()).getString("email")

                        var user = User(name,password,id, phoneNumber, permissionState, job, email)

                        Log.d("RESPONSE: ", id)
                        job_check(user)

                    }catch (e:JSONException){
                        e.printStackTrace()
                    }

                } else {

                    wrong()

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
            if(user.permissionState == false){
                intent = Intent(this, Access_TokenActivity::class.java)
                intent.putExtra("key",user)
                startActivity(intent)

            }
            else if(user.permissionState == true){
                intent = Intent(this, Student_Nav_Activity::class.java)
                intent.putExtra("key",user)
                startActivity(intent)
            }
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

    fun wrong(){
        val builder = AlertDialog.Builder(this)
            .setTitle("로그인 실패")
            .setMessage("정보가 일치하지 않거나 정지된 계정입니다.\n\n[정지는 기준일로 부터 3일 후 09시 이후 해제]")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener{ dialog, which -> })
        builder.show()
    }

}
