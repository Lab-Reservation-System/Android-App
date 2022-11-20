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

class CanUseSeat_Dialog(context : Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog(labNum:String, reservationTodayList : MutableList<Reservation>)
    {
        dialog.setContentView(R.layout.dialog_can_use_seat)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val lab = dialog.findViewById<TextView>(R.id.labNumber)
        lab.setText(labNum)



        val cancel_btn = dialog.findViewById<Button>(R.id.cancel_btn)

        cancel_btn.setOnClickListener() {
            dialog.dismiss()
        }


    }

    interface OnDialogClickListener
    {
        fun onClicked()
    }

}