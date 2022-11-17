package com.deu.lab_reservation_system_android.assist_fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deu.lab_reservation_system_android.activity.User.Password_CheckActivity
import com.deu.lab_reservation_system_android.activity.User.LoginActivity
import com.deu.lab_reservation_system_android.activity.nav.Assistant_Nav_Activity
import com.deu.lab_reservation_system_android.databinding.FragmentAssistMypageBinding

import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class assist_MyPageFragment : Fragment() {

    private var mBinding : FragmentAssistMypageBinding? = null
    var token:String = "123"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("조교로그", "onCreateView: 마이페이ㅣㅈ")

        var user = (activity as Assistant_Nav_Activity).getUser()

        val binding = FragmentAssistMypageBinding.inflate(inflater, container, false)
        mBinding = binding

        binding.userId.setText(user.id)
        binding.userEmail.setText(user.email)
        binding.userName.setText(user.name)
        binding.userPhone.setText(user.phoneNumber)

        binding.logoutBtn.setOnClickListener(){
            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.infoChangeBtn.setOnClickListener(){
            var intent = Intent(activity, Password_CheckActivity::class.java)
            intent.putExtra("key",user)
            startActivity(intent)
        }

        binding.showToken.setOnClickListener(){
            getToken()
        }

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
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
                        alertDelete()
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

    fun alertDelete(){
        val builder = AlertDialog.Builder(activity)
            .setTitle("                       토큰 확인")
            .setMessage("                              현재 토큰 값\n                                 "+ token)

            .setPositiveButton("닫기", DialogInterface.OnClickListener{ dialog, which -> })
            .setNegativeButton("재발급", DialogInterface.OnClickListener{ dialog, which -> changeToken()})

        builder.show()


    }

    fun changeToken() {
        val call = RetrofitBuilder.api_token.changeTokenResponse()
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
                        alertDelete()
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