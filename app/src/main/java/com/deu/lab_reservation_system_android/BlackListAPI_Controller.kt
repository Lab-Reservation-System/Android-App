package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.BlackList_user
import retrofit2.Call
import retrofit2.http.GET

interface BlackListAPI_Controller {

    @GET("/api/blacklist/report/show")
    fun getBlackListResponse(): Call<List<BlackList_user>>
}