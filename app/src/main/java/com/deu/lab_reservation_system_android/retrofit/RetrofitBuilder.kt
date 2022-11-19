package com.deu.lab_reservation_system_android.retrofit

import com.deu.lab_reservation_system_android.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitBuilder {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://113.198.235.233:8080/") // 요청 보내는 API 서버 url. /로 끝나야 함함
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()) // Gson을 역직렬화
        .build()

    var api_user: UserAPI_Controller
    init{
        api_user = retrofit.create(UserAPI_Controller::class.java)
    }

    var api_token: TokenAPI_Controller
    init{
        api_token = retrofit.create(TokenAPI_Controller::class.java)
    }

    var api_lab: LabAPI_Controller
    init {
        api_lab = retrofit.create(LabAPI_Controller::class.java)
    }

    var api_blacklist: BlackListAPI_Controller
    init{
        api_blacklist = retrofit.create(BlackListAPI_Controller::class.java)
    }

    var api_classes: ClassesAPI_Controller
    init{
        api_classes = retrofit.create(ClassesAPI_Controller::class.java)
    }

}