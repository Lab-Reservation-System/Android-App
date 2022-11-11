package com.deu.lab_reservation_system_android.model

data class User(
    var email: String,
    var id: String,
    var job: String,
    var name: String,
    var password: String,
    var permissionState: Boolean,
    var phoneNumber: String
)