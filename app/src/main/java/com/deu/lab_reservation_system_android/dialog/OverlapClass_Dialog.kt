package com.deu.lab_reservation_system_android.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.Schedule

class OverlapClass_Dialog (context : Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }

    fun showDialog(alreadyExist : MutableSet<String>) {

        dialog.setContentView(R.layout.dialog_overlap_class)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)

        dialog.setCancelable(true)

        val classname = dialog.findViewById<EditText>(R.id.overlap_List)

        alreadyExist.forEach({ it ->
            //Log.d("TAG", "showDialog: ${it}")
            classname.append(it+"\n")
        })

        val cancel_btn = dialog.findViewById<Button>(R.id.cancel_btn)


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