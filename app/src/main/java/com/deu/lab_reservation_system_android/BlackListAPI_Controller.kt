package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.BlackList_user
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BlackListAPI_Controller {


    // 블랙리스트 조회
    @GET("/api/blacklist/report/show")
    fun getBlackListResponse(): Call<List<BlackList_user>>


    // 블랙리스트 신고
    @GET("/api/blacklist/report/{userId}")
    fun getuserReprot(@Path("userId") userid: String): Call<String>
}