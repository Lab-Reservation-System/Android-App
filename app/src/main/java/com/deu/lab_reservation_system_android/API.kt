package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.Dto.LoginDto
import retrofit2.Call
import retrofit2.http.*

public interface API {
    // login
    @POST("/api/user/login")
    fun getLoginResponse(@Body logindto : LoginDto): Call<String>
}