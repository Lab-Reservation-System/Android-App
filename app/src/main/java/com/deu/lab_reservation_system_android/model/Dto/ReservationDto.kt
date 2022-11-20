package com.deu.lab_reservation_system_android.model.Dto

data class ReservationDto(
    val name: String,
    val time: String,
    val id: String,
    val date: String,
    val permissionState: Boolean,
    val labNumber: String,
    val seat: String

)