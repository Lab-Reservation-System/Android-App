package com.deu.lab_reservation_system_android.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.adapter.TodayReservationListTableRowAdapter
import com.deu.lab_reservation_system_android.model.Dto.ReservationDto
import com.deu.lab_reservation_system_android.model.Reservation
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.model.row_format.today_Reserve_show_format
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MyReservation_Dialog (context : Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: MyReservation_Dialog.OnDialogClickListener
    private lateinit var reservationlist_RowAdapter: TodayReservationListTableRowAdapter

    private lateinit var tableRecyclerView : RecyclerView

    lateinit var response_ReservationList : MutableList<Reservation>

    var selectingID = "z"
    var old : View? = null
    var old_position : Int = -1

    var my_ReservationList : MutableList<Reservation> = mutableListOf()
    lateinit var UserID : String

    fun setOnClickListener(listener: MyReservation_Dialog.OnDialogClickListener) {
        onClickListener = listener
    }

    fun showDialog(userid : String) {

        UserID = userid
        dialog.setContentView(R.layout.dialog_my_reservation)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()


        get_reservationList()

        val deny_btn = dialog.findViewById<Button>(R.id.select_deny_btn)
        deny_btn.setOnClickListener(){
            if(selectingID == "z")  //????????? ????????????
                Toast.makeText(dialog.context, "?????? ??????????????????", Toast.LENGTH_SHORT).show()
            else {
                deny_user(selectingID)
                Toast.makeText(dialog.context, "???????????????????????????.", Toast.LENGTH_SHORT).show()
            }
        }

        val cancel_btn = dialog.findViewById<Button>(R.id.cancel_btn)
        cancel_btn.setOnClickListener{
            dialog.dismiss()
        }
    }
    interface OnDialogClickListener
    {
        fun onClicked()
    }


    fun update_Viewer() {

        var reservationList = ArrayList<today_Reserve_show_format>()
        selectingID = "z"

        my_ReservationList?.forEach { it ->
            reservationList.add(
                today_Reserve_show_format(
                    it.name,
                    it.id,
                    it.labNumber,
                    it.seat,
                    it.time,
                    if (it.permissionState == true) "??????" else "?????????"
                )
            )
        }
        tableRecyclerView = dialog.findViewById(R.id.table_recycler_view)
        reservationlist_RowAdapter = TodayReservationListTableRowAdapter(reservationList)
        tableRecyclerView.layoutManager = LinearLayoutManager(dialog.context)
        tableRecyclerView.adapter = reservationlist_RowAdapter

        reservationlist_RowAdapter.setItemClickListener(object: TodayReservationListTableRowAdapter.OnItemClickListener{
            override fun onClick(v: View, i: Int) {

                if(v != old&& i!= old_position){
                    if(old != null)
                        selected_row(old!!)
                    old_position = i
                    old = v
                }
                Log.d("???????????????", "onClick: ${i} ")
                my_ReservationList.forEach { it ->
                    if(it.id==reservationList.get(i).id &&
                        it.labNumber==reservationList.get(i).labNumber &&
                        it.seat==reservationList.get(i).seat &&
                        it.time==reservationList.get(i).time){
                        selectingID = it.reservationNum
                        Log.d("????????? ????????????", "${it.reservationNum}")
                    }
                }
            }
        })
    }
    fun selected_row(v:View){
        v.findViewById<TextView>(R.id.userName).setBackgroundResource(R.drawable.blue_corner)
        v.findViewById<TextView>(R.id.userId).setBackgroundResource(R.drawable.blue_corner)
        v.findViewById<TextView>(R.id.labNum).setBackgroundResource(R.drawable.blue_corner)
        v.findViewById<TextView>(R.id.seatNum).setBackgroundResource(R.drawable.blue_corner)
        v.findViewById<TextView>(R.id.usingTime).setBackgroundResource(R.drawable.blue_corner)
        v.findViewById<TextView>(R.id.status).setBackgroundResource(R.drawable.blue_corner)
    }


    fun get_my(){
        response_ReservationList.forEach({
            if(it.id==UserID)
            {
                my_ReservationList.add(it)
            }
        })
        update_Viewer()
    }

    fun get_reservationList() {
        val call = RetrofitBuilder.api_reservation.getAllReservationResponse()
        Log.d("Watching: ", "??????1")
        call.enqueue(object : Callback<MutableList<Reservation>> { // ????????? ?????? ?????? ?????????

            override fun onResponse( // ????????? ????????? ??????
                call: Call<MutableList<Reservation>>,
                response: Response<MutableList<Reservation>>
            ) {
                if (response.isSuccessful()) { // ?????? ??? ?????? ??????
                    //Log.d("RESPONSE: ", response.body().toString())
                    try {
                        Log.d("Watching: ", "??????2")

                        response_ReservationList = response.body()!!
                        get_my()
//                        Log.d("????????????",response_ReservationList[0].id)
                        //update_Viewer(0)



                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // ?????? ?????? but ?????? ??????
                    Log.d("Watching: ", "??????1")
                    //wrong()

                }
            }

            override fun onFailure(call: Call<MutableList<Reservation>>, t: Throwable) {
                // ????????? ????????? ??????
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "??????2")
            }
        })
    }

    fun deny_user(res_id:String) {
        val call = RetrofitBuilder.api_reservation.getDenyReservation(res_id)
        var temp : Reservation? = null
        Log.d("Watching: ", "??????1")
        call.enqueue(object : Callback<String> { // ????????? ?????? ?????? ?????????

            override fun onResponse( // ????????? ????????? ??????
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // ?????? ??? ?????? ??????

                    try {
                        Log.d("Watching: ", "??????2")

                        my_ReservationList.forEach { it ->
                            if (it.reservationNum == res_id) {
                                Log.d("?????????", "onResponse: ${my_ReservationList.size}")
                                temp = it
                            }

                        }
                        Log.d("?????????", "onResponse: ${my_ReservationList.size}")

                        my_ReservationList.remove(temp)
                        update_Viewer()

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // ?????? ?????? but ?????? ??????
                    Log.d("Watching: ", "??????1")
                    //wrong()

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // ????????? ????????? ??????
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "??????2")

            }
        })
    }

}