package com.deu.lab_reservation_system_android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deu.lab_reservation_system_android.R

// 실습실 현황 조회 화면 (강의실 전체)
class Reservation_AllStatusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_allstatus)
    }
}