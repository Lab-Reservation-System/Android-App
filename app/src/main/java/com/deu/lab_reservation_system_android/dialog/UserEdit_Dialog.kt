package com.deu.lab_reservation_system_android.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserEdit_Dialog(context : Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog(user: User)
    {

        dialog.setContentView(R.layout.dialog_edit_user)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)

        dialog.setCancelable(true)

        val edit_job = dialog.findViewById<EditText>(R.id.user_job)
        val edit_permission = dialog.findViewById<EditText>(R.id.user_permission)

        val edit_name = dialog.findViewById<EditText>(R.id.user_name)
        val edit_id = dialog.findViewById<EditText>(R.id.user_Id)
        val edit_pw = dialog.findViewById<EditText>(R.id.user_pw)
        val edit_phone = dialog.findViewById<EditText>(R.id.user_phone)
        val edit_email = dialog.findViewById<EditText>(R.id.user_email)
        val cancel_btn = dialog.findViewById<Button>(R.id.cancel_btn)
        val save_btn = dialog.findViewById<Button>(R.id.info_save_btn)
        val delete_btn = dialog.findViewById<Button>(R.id.info_delete_btn)

        var permission:String
        if(user.permissionState == true) {  permission = "O" }
        else { permission = "X"}

        edit_job.setText(user.job)
        edit_permission.setText(permission)
        edit_name.setText(user.name)
        edit_id.setText(user.id)
        edit_pw.setText(user.password)
        edit_phone.setText(user.phoneNumber)
        edit_email.setText(user.email)

        dialog.show()


        cancel_btn.setOnClickListener() {
            dialog.dismiss()
        }

        save_btn.setOnClickListener(){

            if( edit_name.text.toString().length == 0 ||
                edit_id.text.toString().length == 0 ||
                edit_pw.text.toString().length == 0 ||
                edit_email.text.toString().length == 0 ||
                edit_phone.text.toString().length == 0 ){
                val builder = AlertDialog.Builder(dialog.context)
                    .setTitle("오류")
                    .setMessage("모든 내용을 입력하세요")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                builder.show()
            }else{
                user.name = edit_name.text.toString()
                user.id = edit_id.text.toString()
                user.password = edit_pw.text.toString()
                user.email = edit_email.text.toString()
                user.phoneNumber = edit_phone.text.toString()
                save_edit(user)
            }
            onClickListener.onClicked(0)
            dialog.dismiss()
        }
        delete_btn.setOnClickListener(){
            DeleteUser(user.id)
            onClickListener.onClicked(1)
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked(i:Int)
    }

    fun save_edit(user: User) {
        val call = RetrofitBuilder.api_user.EditUserResponse(user.id,user)
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    try {
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
