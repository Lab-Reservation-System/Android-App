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
import com.deu.lab_reservation_system_android.nav.Student_Nav_Activity
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Access_TokenActivity : AppCompatActivity() {

    lateinit var token:String

    lateinit var data: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_token)

        data = intent.getSerializableExtra("key") as User

        getToken()

        val useRequest_btn = findViewById<Button>(R.id.useRequestBtn)
        val back_btn = findViewById<Button>(R.id.backPageBtn)
        var input_token = findViewById<EditText>(R.id.inputToken)


        useRequest_btn.setOnClickListener {

            if(token == input_token.text.toString()) { AccessToken(data.id) }
            else { wrong() }

        }

        back_btn.setOnClickListener {
            finish()
        }

    }


    fun getToken() {
        val call = RetrofitBuilder.api_token.getTokenResponse()
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    try {
                        //어떻게 응답
                        token = JSONObject(response.body()).getString("value")
                        Log.d("RESPONSE: ", token)

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

    fun wrong(){
        val builder = AlertDialog.Builder(this)
            .setTitle("사용 인증 실패")
            .setMessage("잘못된 토큰입니다.")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener{ dialog, which -> })
        builder.show()
    }

    fun AccessToken(user_id: String) {
        val call = RetrofitBuilder.api_user.getUseAccessResponse(user_id)
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    try {
                        access_user()

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    wrong()
                    Log.d("RESPONSE", "FAILURE")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }

    fun access_user() {
        var intent = Intent(this, Student_Nav_Activity::class.java)
        intent.putExtra("key", data)
        startActivity(intent)
    }

}