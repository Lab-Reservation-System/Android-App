package com.deu.lab_reservation_system_android.activity.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.activity.User.DataReservationHistory
import com.deu.lab_reservation_system_android.activity.User.ReservationHistoryTableRowAdapter

// 내 예약 내역 조회
class Check_Reservation_HistoryActivity : AppCompatActivity() {

    private lateinit var tableRecyclerView : RecyclerView
    private var userList = ArrayList<DataReservationHistory>()
    private lateinit var tableRowAdapter: ReservationHistoryTableRowAdapter
    private lateinit var user : DataReservationHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_reservation_history)

        userList.add(DataReservationHistory(false, "915-10", "16:00", true))
        userList.add(DataReservationHistory(true, "918-10", "16:00", true))
        userList.add(DataReservationHistory(false, "915-10", "16:00", true))


        tableRecyclerView = findViewById(R.id.history_table_recycler_view)
        tableRowAdapter = ReservationHistoryTableRowAdapter(userList)

        tableRecyclerView.layoutManager = LinearLayoutManager(this)
        tableRecyclerView.adapter = tableRowAdapter
    }
}