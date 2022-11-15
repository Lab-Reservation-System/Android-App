package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.activity.student.model.Dto.LoginDto
import com.deu.lab_reservation_system_android.activity.student.model.Dto.SignupDto
import retrofit2.Call
import retrofit2.http.*

interface UserAPI_Controller {
    // login
    @POST("/api/user/login")
    fun getLoginResponse(@Body logindto : LoginDto): Call<String>

    @POST("/api/user/create")
    fun getSignupResponse(@Body signupDto: SignupDto): Call<String>

    @PATCH("/api/user/permission/{userid}")
    fun getUseAccessResponse(@Path("userid") userid: String): Call<String>
}