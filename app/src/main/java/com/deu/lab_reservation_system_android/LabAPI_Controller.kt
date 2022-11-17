package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.Dto.TodayReservationDto
import retrofit2.Call
import retrofit2.http.GET

interface LabAPI_Controller {

    // 좌석 조회
    @GET("/api/lab/show")
    fun getReservationStatusResponse () : Call<List<TodayReservationDto>>

}