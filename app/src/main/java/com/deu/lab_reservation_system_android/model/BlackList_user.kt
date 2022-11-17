package com.deu.lab_reservation_system_android.model

data class BlackList_user(
    val id: String,
    val numberOfReport: Int,
    val reportOfToday: Int,
    val restrictionEndDate: String
)