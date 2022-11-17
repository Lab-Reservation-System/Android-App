package com.deu.lab_reservation_system_android.model

// 예약 내역 Dto 같은 느낌
data class myReservation(
    var checkBox: Boolean,
    var seat: String,
    var time: String,
    var check: Boolean
)
