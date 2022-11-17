package com.deu.lab_reservation_system_android.dialog

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.activity.BlackListTableRowAdapter
import com.deu.lab_reservation_system_android.model.BlackList_user
import com.deu.lab_reservation_system_android.model.blackList_show_format

class BlackList_Dialog(context : Context) {

    private lateinit var tableRecyclerView : RecyclerView
    private var blackList = ArrayList<blackList_show_format>()
    private lateinit var blacklist_tableRowAdapter: BlackListTableRowAdapter
    private lateinit var black_user : blackList_show_format

    private val dialog = Dialog(context)
    private lateinit var onClickListener: BlackList_Dialog.OnDialogClickListener

    fun setOnClickListener(listener: BlackList_Dialog.OnDialogClickListener)
    {
        onClickListener = listener
    }
    fun showDialog() {

        dialog.setContentView(R.layout.dialog_blaicklist)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)


        blackList.add(blackList_show_format("20189310",14,"2022-11-22"))
        blackList.add(blackList_show_format("20189355",14,"2022-11-22"))

        tableRecyclerView = dialog.findViewById(R.id.blackList_table_recycler_view)
        blacklist_tableRowAdapter = BlackListTableRowAdapter(blackList)

        tableRecyclerView.layoutManager = LinearLayoutManager(dialog.context)
        tableRecyclerView.adapter = blacklist_tableRowAdapter

        dialog.show()

        val back_btn = dialog.findViewById<Button>(R.id.back_btn)

        back_btn.setOnClickListener() {
            dialog.dismiss()
        }


    }

    interface OnDialogClickListener
    {
        fun onClicked(i:Int)
    }

}