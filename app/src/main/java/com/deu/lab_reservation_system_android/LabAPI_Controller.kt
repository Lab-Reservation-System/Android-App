package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.Dto.Reservation
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface LabAPI_Controller {

    // 실시간
    @GET("/api/lab/show")
    fun getReservationStatusResponse () : Call<List<Reservation>>

    // 모든 예약
    @GET("/api/reservation/show/all")
    fun getAllReservationResponse () : Call<List<Reservation>>

    // 오늘 예약만
    @GET("/api/reservation/show/today")
    fun getTodayReservationResponse () : Call<MutableList<Reservation>>

    // 사용자 예약 승인
    @GET("/api/reservation/permit/{reservationNum}")
    fun getAllowReservation (@Path("reservationNum") reservation_num: String): Call<String>

    // 모든 예약 승인
    @GET("/api/reservation/permit/all")
    fun getAllAllowReservation (): Call<String>

    // 예약 취소
    @DELETE("/api/reservation/delete/{reservationNum}")
    fun getDenyReservation (@Path("reservationNum") reservation_num: String): Call<String>
}