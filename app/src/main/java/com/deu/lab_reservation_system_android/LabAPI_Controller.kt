package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.Dto.Reservation
import retrofit2.Call
import retrofit2.http.GET

interface LabAPI_Controller {

    // 실시간
    @GET("/api/lab/show")
    fun getReservationStatusResponse () : Call<List<Reservation>>

    @GET("/api/reservation/show/all")
    fun getAllReservationResponse () : Call<List<Reservation>>
}