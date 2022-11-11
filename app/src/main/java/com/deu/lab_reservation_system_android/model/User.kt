package com.deu.lab_reservation_system_android.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("userId")
    val userId: Int,

    @SerializedName("id")
    val id: String,

    @SerializedName("body")
    val body: String

)