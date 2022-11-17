package com.deu.lab_reservation_system_android.model

data class Reservation(
    val date: String,
    val id: String,
    val labNumber: String,
    val name: String,
    val permissionState: Boolean,
    val reservationNum: Int,
    val seat: String,
    val time: String
)