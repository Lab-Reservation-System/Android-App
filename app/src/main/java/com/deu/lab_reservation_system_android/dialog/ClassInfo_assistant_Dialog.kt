package com.deu.lab_reservation_system_android.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.Schedule
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassInfo_assistant_Dialog (context : Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }

    fun showDialog(schedule: Schedule) {

        dialog.setContentView(R.layout.dialog_class_info_assistant)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)

        dialog.setCancelable(true)

        val classname = dialog.findViewById<EditText>(R.id.class_name)
        val proname = dialog.findViewById<EditText>(R.id.pro_name)
        val date = dialog.findViewById<EditText>(R.id.date)
        val time = dialog.findViewById<EditText>(R.id.time)
        val cancel_btn = dialog.findViewById<Button>(R.id.cancel_btn)
        val delete_btn = dialog.findViewById<Button>(R.id.delete_btn)

        classname.setText(schedule.className)
        proname.setText(schedule.proName)
        date.setText(schedule.date+"요일")
        time.setText(schedule.time)

        dialog.show()



        cancel_btn.setOnClickListener() {
            dialog.dismiss()
        }
        delete_btn.setOnClickListener() {
            if(schedule.regularClassNum == 0)
            {
                val builder = AlertDialog.Builder(dialog.context)
                    .setTitle("정말 지우시겠습니까?")
                    .setMessage("해당 시간의 세미나는 지워집니다.")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> DeleteSeminar(schedule.classNum) })
                    .setNegativeButton("닫기", DialogInterface.OnClickListener { dialog, which ->})
                builder.show()

            }
            else{

                val builder = AlertDialog.Builder(dialog.context)
                    .setTitle("정말 지우시겠습니까")
                    .setMessage("해당 시간의 수업은 모두 지워집니다.")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> DeleteClass(schedule.regularClassNum) })
                    .setNegativeButton("닫기", DialogInterface.OnClickListener { dialog, which ->})
                builder.show()
                // 진짜 지울지 다이얼로그
            }

            dialog.dismiss()
        }
    }
    interface OnDialogClickListener
    {
        fun onClicked(i:Int)
    }

    fun DeleteClass(regularNum: Int) {
        val call = RetrofitBuilder.api_classes.DeleteClass(regularNum)
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    try {
                        // 로그인으로 이동
                        onClickListener.onClicked(1)
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
    fun DeleteSeminar(classNum: Int) {
        val call = RetrofitBuilder.api_classes.DeleteSeminar(classNum)
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    Log.d("RESPONSE: ", response.body().toString())
                    try {
                        // 로그인으로 이동
                        onClickListener.onClicked(1)
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