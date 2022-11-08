package com.deu.lab_reservation_system_android

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitBuilder {
    var api: API
    init{
        val retrofit = Retrofit.Builder()
            .baseUrl("http://113.198.235.233:8080/") // 요청 보내는 API 서버 url. /로 끝나야 함함
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()) // Gson을 역직렬화
            .build()
        api = retrofit.create(API::class.java)
    }
}