package com.deu.lab_reservation_system_android.activity.shared

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deu.lab_reservation_system_android.R

// 강의실 시간표 화면
class Lab_ScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_schedule)
    }
}