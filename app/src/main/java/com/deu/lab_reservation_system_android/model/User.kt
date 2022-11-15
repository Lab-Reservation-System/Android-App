package com.deu.lab_reservation_system_android.model

import java.io.Serializable

data class User(

    var name: String,
    var password: String,
    var id: String,
    var phoneNumber : String,
    var permissionState: Boolean,
    var job: String,
    var email: String

) : Serializable