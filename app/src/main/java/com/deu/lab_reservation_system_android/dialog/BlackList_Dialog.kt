package com.deu.lab_reservation_system_android.dialog

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.adapter.BlackListTableRowAdapter
import com.deu.lab_reservation_system_android.model.BlackList_user
import com.deu.lab_reservation_system_android.model.row_format.blackList_show_format
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlackList_Dialog(context : Context) {

    private lateinit var tableRecyclerView : RecyclerView
    private var blackList = ArrayList<blackList_show_format>()
    private lateinit var blacklist_tableRowAdapter: BlackListTableRowAdapter
    //private lateinit var black_user : blackList_show_format

    lateinit var response_blackList : List<BlackList_user>

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

        get_blackList()



        tableRecyclerView = dialog.findViewById(R.id.blackList_table_recycler_view)
        blacklist_tableRowAdapter = BlackListTableRowAdapter(blackList)

        tableRecyclerView.layoutManager = LinearLayoutManager(dialog.context)
        tableRecyclerView.adapter = blacklist_tableRowAdapter

        Handler(Looper.getMainLooper()).postDelayed({ dialog.show() }, 500)



        val back_btn = dialog.findViewById<Button>(R.id.back_btn)

        back_btn.setOnClickListener() {
            dialog.dismiss()
        }


    }

    interface OnDialogClickListener
    {
        fun onClicked(i:Int)
    }

    fun get_blackList() {
        val call = RetrofitBuilder.api_blacklist.getBlackListResponse()
        Log.d("Watching: ", "성공1")
        call.enqueue(object : Callback<List<BlackList_user>> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<List<BlackList_user>>,
                response: Response<List<BlackList_user>>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    //Log.d("RESPONSE: ", response.body().toString())
                    try {
                        Log.d("Watching: ", "성공2")

                        response_blackList = response.body()!!

                        Log.d("테스트용",response_blackList.get(0).id)

                        response_blackList?.forEach { it ->
                            blackList.add(blackList_show_format(it.id, it.numberOfReport, it.restrictionEndDate))
                            //blackList.add(blackList_show_format("21332", 1, "213"))
                        }



                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("Watching: ", "실패1")
                    //wrong()

                }
            }

            override fun onFailure(call: Call<List<BlackList_user>>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "실패2")
            }
        })
    }

}