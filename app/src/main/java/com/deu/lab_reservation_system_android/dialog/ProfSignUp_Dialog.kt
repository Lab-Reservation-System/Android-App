package com.deu.lab_reservation_system_android.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.Dto.SignupDto
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfSignUp_Dialog(context : Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }
    fun showDialog() {

        dialog.setContentView(R.layout.dialog_signup_professor)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()


        val prof_name = dialog.findViewById<EditText>(R.id.name)
        val prof_id = dialog.findViewById<EditText>(R.id.pro_id)
        val prof_pw = dialog.findViewById<EditText>(R.id.password)
        val prof_email = dialog.findViewById<EditText>(R.id.email)
        val prof_phone = dialog.findViewById<EditText>(R.id.phone)

        val signup_btn = dialog.findViewById<Button>(R.id.signup_btn)
        val back_btn = dialog.findViewById<Button>(R.id.back_btn)

        back_btn.setOnClickListener() {
            dialog.dismiss()
        }

        signup_btn.setOnClickListener() {

            if (prof_name.text.toString().length == 0 ||
                prof_id.text.toString().length == 0 ||
                prof_pw.text.toString().length == 0 ||
                prof_email.text.toString().length == 0 ||
                prof_phone.text.toString().length == 0
            ) {

            } else{
                val signupdto = SignupDto(prof_id.text.toString(),
                                          prof_name.text.toString() ,
                                          prof_pw.text.toString(),
                                          prof_phone.text.toString(),
                                          prof_email.text.toString(),
                                      "professor", true)
                SignUp(signupdto)
                dialog.dismiss()
            }


        }
    }
    interface OnDialogClickListener
    {
        fun onClicked(i:Int)
    }

    fun SignUp(signupDto: SignupDto) {
        val call = RetrofitBuilder.api_user.getSignupResponse(signupDto)
        call.enqueue(object : Callback<String> { // ????????? ?????? ?????? ?????????

            override fun onResponse( // ????????? ????????? ??????
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // ?????? ??? ?????? ??????
                    Log.d("RESPONSE: ", response.body().toString())
                    try {
                        //????????? ??????
                        onClickListener.onClicked(1)

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // ?????? ?????? but ?????? ??????
                    Log.d("RESPONSE", "FAILURE")
                    onClickListener.onClicked(0)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // ????????? ????????? ??????
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }



}