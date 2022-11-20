package com.deu.lab_reservation_system_android.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.Reservation
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeatInfo_Dialog(context : Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog(todayReservation: Reservation)
    {
        dialog.setContentView(R.layout.dialog_seat_info)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        var name = dialog.findViewById<TextView>(R.id.userName)
        var id = dialog.findViewById<TextView>(R.id.userId)

        id.setText(todayReservation.id)
        name.setText(todayReservation.name)

        val ok_btn = dialog.findViewById<Button>(R.id.okBtn)
        val reposrtBtn = dialog.findViewById<Button>(R.id.userReport)


        ok_btn.setOnClickListener() {
            dialog.dismiss()
        }

        reposrtBtn.setOnClickListener(){
            // 학번으로 신고
            report(todayReservation.id)
            onClickListener.onClicked()
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked()
    }

    fun report(stu_id: String) {
        val call = RetrofitBuilder.api_blacklist.getuserReprot(stu_id)
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
}