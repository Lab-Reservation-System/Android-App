package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.Classes
import com.deu.lab_reservation_system_android.model.Dto.ClassesDto
import retrofit2.Call
import retrofit2.http.*

interface ClassesAPI_Controller {
    @POST("/api/class/create")
    fun getCreateClassResponse(@Body classes: MutableList<ClassesDto>): Call<String>

    @GET("/api/class/index")
    fun getAllSeminarClassResponse() : Call<List<Classes>>

    // 삭제
    @DELETE("/api/class/delete/seminar/{classNum}")
    fun DeleteSeminar(@Path("classNum") classnum: Int): Call<String>

    @DELETE("/api/class/delete/regular/{regularClassNum}")
    fun DeleteClass(@Path("regularClassNum") regularnum: Int): Call<String>
}