package com.deu.lab_reservation_system_android.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.adapter.ReservationListTableRowAdapter
import com.deu.lab_reservation_system_android.model.Reservation
import com.deu.lab_reservation_system_android.model.row_format.reservation_show_format
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class show_All_Reservation_Activity : AppCompatActivity() {

    var dateString:String = ""
    private lateinit var tableRecyclerView : RecyclerView

    private lateinit var reservationlist_tableRowAdapter: ReservationListTableRowAdapter

    lateinit var response_ReservationList : MutableList<Reservation>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_reservation)

        val date = findViewById<Button>(R.id.date)
        val init = findViewById<Button>(R.id.init)
        val back_btn = findViewById<Button>(R.id.back_btn)
        val date_text = findViewById<TextView>(R.id.datetext)

        get_reservationList()


        date.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}-${month + 1}-${dayOfMonth}"
                date_text.setText(dateString)
                update_Viewer(dateString)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        init.setOnClickListener(){
            date_text.setText("-")
            update_Viewer("")
        }

        back_btn.setOnClickListener{
            finish()
        }
    }

    fun update_Viewer(date:String){

        var reservationList = ArrayList<reservation_show_format>()


        if(date.length==0){  //init 조회
            response_ReservationList?.forEach { it ->
                reservationList.add(reservation_show_format(it.date, it.name, it.id,it.labNumber,it.seat,it.time,if(it.permissionState == true) "승인" else "미승인"))
            }
        }
        else {
            response_ReservationList?.forEach { it ->
                if (it.date==date)//키워드가 포함된다면
                    reservationList.add(reservation_show_format(it.date, it.name, it.id,it.labNumber,it.seat,it.time,if(it.permissionState == true) "승인" else "미승인"))
            }
        }

        tableRecyclerView = findViewById(R.id.table_recycler_view)
        reservationlist_tableRowAdapter = ReservationListTableRowAdapter(reservationList)

        tableRecyclerView.layoutManager = LinearLayoutManager(this)
        tableRecyclerView.adapter = reservationlist_tableRowAdapter
    }

    fun get_reservationList() {
        val call = RetrofitBuilder.api_reservation.getAllReservationResponse()
        Log.d("Watching: ", "성공1")
        call.enqueue(object : Callback<MutableList<Reservation>> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<MutableList<Reservation>>,
                response: Response<MutableList<Reservation>>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    //Log.d("RESPONSE: ", response.body().toString())
                    try {
                        Log.d("Watching: ", "성공2")

                        response_ReservationList = response.body()!!

                        Log.d("테스트용",response_ReservationList.get(0).id)
                        update_Viewer("")



                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("Watching: ", "실패1")
                    //wrong()

                }
            }

            override fun onFailure(call: Call<MutableList<Reservation>>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "실패2")
            }
        })
    }
}