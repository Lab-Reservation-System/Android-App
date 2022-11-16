package com.deu.lab_reservation_system_android

import com.deu.lab_reservation_system_android.model.Dto.LoginDto
import com.deu.lab_reservation_system_android.model.Dto.SignupDto
import com.deu.lab_reservation_system_android.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserAPI_Controller {
    // login
    @POST("/api/user/login")
    fun getLoginResponse(@Body logindto : LoginDto): Call<String>

    // 계정 생성
    @POST("/api/user/create")
    fun getSignupResponse(@Body signupDto: SignupDto): Call<String>


    // 학생 사용 승인
    @PATCH("/api/user/permission/{userid}")
    fun getUseAccessResponse(@Path("userid") userid: String): Call<String>


    // 수정
    @PATCH("/api/user/edit/{id}")
    fun EditUserResponse(@Path("id") userid: String, @Body user: User): Call<String>


    // 삭제
    @DELETE("/api/user/delete/{id}")
    fun DeleteUserResponse(@Path("id") userid: String): Call<String>

    // 전체 조회
    @GET("/api/user/index")
    fun getAllUserResponse(): Call<List<User>>


}