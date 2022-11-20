package com.deu.lab_reservation_system_android.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.Schedule

class ClassInfo_Dialog (context : Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }

    fun showDialog(schedule: Schedule) {

        dialog.setContentView(R.layout.dialog_class_info)
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

        classname.setText(schedule.className)
        proname.setText(schedule.proName)
        date.setText(schedule.date+"요일")
        time.setText(schedule.time)

        dialog.show()



        cancel_btn.setOnClickListener() {
            dialog.dismiss()
        }
    }
    interface OnDialogClickListener
    {
        fun onClicked()
    }
}