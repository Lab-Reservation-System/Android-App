package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.Dto.LoginDto
import com.deu.lab_reservation_system_android.model.Dto.SignupDto
import retrofit2.Call
import retrofit2.http.*

public interface UserAPI_Service {
    // login
    @POST("/api/user/login")
    fun getLoginResponse(@Body logindto : LoginDto): Call<String>

    @POST("/api/user/create")
    fun getSignupResponse(@Body signupDto: SignupDto): Call<String>

}