package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.Dto.LoginDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface TokenAPI_Controller {

    @GET("/api/token/show")
    fun getTokenResponse(): Call<String>

}