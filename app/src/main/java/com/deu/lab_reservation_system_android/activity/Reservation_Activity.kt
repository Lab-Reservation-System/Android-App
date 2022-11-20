package com.deu.lab_reservation_system_android.activity

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.dialog.CanUseSeat_Dialog
import com.deu.lab_reservation_system_android.dialog.OverlapClass_Dialog
import com.deu.lab_reservation_system_android.model.Classes
import com.deu.lab_reservation_system_android.model.Dto.ReservationDto
import com.deu.lab_reservation_system_android.model.Reservation
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class Reservation_Activity : AppCompatActivity() {

    lateinit var user: User //가져온 예약할 사용자 정보
    lateinit var today : String

    lateinit var response_ReservationList : MutableList<Reservation> // 오늘 예약
    var reservationTodayList : MutableList<Reservation> = mutableListOf() // 해당 실습실 예약 정보


    lateinit var classList : List<Classes>  // 수업 전체
    var todayclassList : MutableList<Classes> = mutableListOf() // 오늘자 수업


    var reservationList : MutableList<ReservationDto> = mutableListOf() // 내가 신청한 예약 정보

    lateinit var clickedLab : String  // 내가 선택한 실습실 번호

    var TAG = "실습실 예약"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        //오늘 날짜 구하기
        val currentTime : Long = System.currentTimeMillis() // ms로 반환
        val dataFormat = SimpleDateFormat("yyyy-MM-dd")
        today = dataFormat.format(currentTime)
        Log.d(TAG, "오늘 날짜: "+today)

        //이전 화면에서 유저 정보 가져오기
        user = intent.getSerializableExtra("key") as User

        get_ClassesList() // 모든 수업 요청해서 받아오기 -> 오늘자 수업

        //라디오 그룹 - 버튼
        var radio_group = findViewById<RadioGroup>(R.id.radioGroup)
        var radio915 = findViewById<RadioButton>(R.id.radioButton915)
        var radio916 = findViewById<RadioButton>(R.id.radioButton916)
        var radio918 = findViewById<RadioButton>(R.id.radioButton918)
        var radio911 = findViewById<RadioButton>(R.id.radioButton911)

        clickedLab = "915"
        radio_group.setOnCheckedChangeListener{ radio_group, i ->
            when(i){
                radio915.id -> {clickedLab = "915"}
                radio916.id -> {clickedLab = "916"}
                radio918.id -> {clickedLab = "918"}
                radio911.id -> {clickedLab = "911"}
            }
        }

        // 체크박스 리스트로 바인딩
        var packName : String  = this.packageName // 패키지 이름
        var checkBox = arrayOfNulls<CheckBox>(32)   // 체크박스
        for(i in 0..31){
            var resName = "@id/check"+i.toString()
            var resId = getResources().getIdentifier(resName, "id", packName)
            checkBox[i] = findViewById(resId)

            checkBox[i]?.setOnClickListener{
                Log.d("체크박스 클릭 리스너 : ", "onCreate: ${i}번째 클릭")
            }
        }

        // 선택 완료 시
        val select_btn = findViewById<Button>(R.id.select_btn)  // 선택 완료 버튼
        select_btn.setOnClickListener {
            var flag = 0 //클릭한 것이 있는지 확인
            reservationList = mutableListOf()
            checkBox.forEach { it ->
                if( it?.isChecked()  == true)
                {
                    flag = 1
                    if(it.text.toString().compareTo("17:00") < 0 && it.text.toString() != "00:00" && it.text.toString() != "00:30") { //일과시간
                        Log.d("체크박스 리스너 : ", "일과시간 ${clickedLab}-${it.text} 클릭됨")
                        reservationList.add(ReservationDto(user.name, it.text.toString(), user.id,today, true,clickedLab,""))
                    }

                    else { //비일과시간
                        Log.d("체크박스 리스너 : ", "비일과시간 ${clickedLab}-${it.text} 클릭됨")
                        reservationList.add(ReservationDto(user.name, it.text.toString(), user.id,today, false,clickedLab,""))
                    }
                }
            }
            if(flag==0) // 아무것도 선택 안함
            {
                val builder = AlertDialog.Builder(this)
                    .setTitle("입력 오류")
                    .setMessage("시간을 선택해주세요")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                builder.show()
            }
            else if(compareWithClass()==0)
                get_reservationList()
            //finish()
        }




        val back_btn = findViewById<Button>(R.id.back_btn)  // 취소 버튼
        back_btn.setOnClickListener {
            finish()
        }
    }



    fun get_ClassesList(){  // 모든 수업 가져오기
        val call = RetrofitBuilder.api_classes.getAllSeminarClassResponse()
        Log.d("Watching: ", "성공1")
        call.enqueue(object : Callback<List<Classes>> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<List<Classes>>,
                response: Response<List<Classes>>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    //Log.d("RESPONSE: ", response.body().toString())
                    try {
                        Log.d("Watching: ", "성공2")

                        classList = response.body()!!

                        get_todayClass() //오늘 수업만

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("Watching: ", "실패1")


                }
            }

            override fun onFailure(call: Call<List<Classes>>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "실패2")
            }
        })
    }

    fun get_todayClass(){ //오늘 수업만으로 리스트 만들기
        todayclassList = mutableListOf()
        classList.forEach({ it ->
            if(it.date == today)
                todayclassList.add(it)
        })
    }

    //수업이랑 비교해서 수업 겹치는지 확인
    fun compareWithClass() : Int
    {
        var flag = 0
        var alreadyExist : MutableSet<String> = mutableSetOf()
        for(i in reservationList) {
            for (j in todayclassList){
                if(i.labNumber==j.labNumber) {
                    var reTime = i.time.split(':')
                    if (reTime[0].toInt() == j.time.toInt()) {
                        Log.d("수업겹침 테스트", "compareWithClass: ${j.time}:00 [${j.time}-${j.className}]이랑 시간 겹침")
                        alreadyExist.add("${j.time}:00 [${j.time}-${j.className}]")
                        flag = 1
                        //수업 겹침
                    }
                }
            }
        }
        if(flag == 1) {
            val dialog = OverlapClass_Dialog(this)
            dialog.showDialog(alreadyExist)
        }
        return flag
    }

    fun CantSeatLabList(){   // 해당하는 강의실 예약만 다이얼로그에 들고가기
        reservationTodayList = mutableListOf()
        response_ReservationList.forEach({ it ->
            if(it.labNumber == clickedLab)
            {
                reservationTodayList.add(it)
            }
        })

        val dialog = CanUseSeat_Dialog(this)
        dialog.showDialog(clickedLab, reservationTodayList, reservationList)




    }


    fun get_reservationList() { //오늘 예약 가져오기
        val call = RetrofitBuilder.api_reservation.getTodayReservationResponse()
        Log.d("Watching: ", "성공1")
        call.enqueue(object : Callback<MutableList<Reservation>> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<MutableList<Reservation>>,
                response: Response<MutableList<Reservation>>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    //Log.d("RESPONSE: ", response.body().toString())
                    try {
                        Log.d("Watching: ", "성공2")

                        response_ReservationList = response.body()!!
                        CantSeatLabList()

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("Watching: ", "실패1")
                    //wrong()

                }
            }

            override fun onFailure(call: Call<MutableList<Reservation>>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "실패2")
            }
        })
    }




}
