package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.Dto.ClassesDto
import com.deu.lab_reservation_system_android.model.Dto.SignupDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ClassesAPI_Controller {
    @POST("/api/class/create")
    fun getCreateClassResponse(@Body classes: MutableList<ClassesDto>): Call<String>
}