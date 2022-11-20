package com.deu.lab_reservation_system_android.model

data class Schedule (
    var regularClassNum : Int,
    var classNum : Int,
    var className : String,
    var proName : String,
    var time: String,
    var date: String,
    var count : Int
)