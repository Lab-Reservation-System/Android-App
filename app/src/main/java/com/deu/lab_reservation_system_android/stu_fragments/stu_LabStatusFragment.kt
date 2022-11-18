package com.deu.lab_reservation_system_android.stu_fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.activity.student.Lab_ReservationActivity
import com.deu.lab_reservation_system_android.databinding.FragmentStuLabstatusBinding
import com.deu.lab_reservation_system_android.dialog.BlackList_Dialog
import com.deu.lab_reservation_system_android.dialog.ProfSignUp_Dialog
import com.deu.lab_reservation_system_android.dialog.SeatInfo_Dialog
import com.deu.lab_reservation_system_android.model.Dto.TodayReservationDto
import com.deu.lab_reservation_system_android.model.user_show_format
import com.deu.lab_reservation_system_android.nav.Student_Nav_Activity
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import kotlinx.coroutines.delay
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class stu_LabStatusFragment : Fragment() {

    private var mBinding : FragmentStuLabstatusBinding? = null
    lateinit var response_userList: List<TodayReservationDto>
    lateinit var binding : FragmentStuLabstatusBinding

    // 강의실 현황
    private var lab_911: MutableList<String> = mutableListOf()
    private var lab_915: MutableList<String> = mutableListOf()
    private var lab_916: MutableList<String> = mutableListOf()
    private var lab_918: MutableList<String> = mutableListOf()

    // 강의실 좌석에 따른 이름 lab_915_name
    private var lab_915_adminName = mutableListOf<String>()
    private var lab_916_adminName = mutableListOf<String>()
    private var lab_918_adminName = mutableListOf<String>()
    private var lab_911_adminName = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("로그", "onCreateView: 실습실현황")
        var user = (activity as Student_Nav_Activity).getUser()

        binding = FragmentStuLabstatusBinding.inflate(inflater, container, false)
        mBinding = binding

        Log.d("로그", binding.labNumber.text.toString())

        Log.d("테스트로그", "labStatus 초기화 전 lab 915 현재 인원 수 " + lab_915.size.toString())
        labStatus(binding.labNumber.text.toString())

        Log.d("테스트로그", "labStatus 초기화 후 lab 915 현재 인원 수 " + lab_915.size.toString())


        //실습실 뒤로 버튼 눌렀을 때
        binding.backLabNumber.setOnClickListener {
            binding.labFrame.removeView(binding.labFrame)


            // 실습실에 따라 화면 다르게 하는 부분
            when(binding.labNumber.text) {
                "915" -> {
                    // 실습실 텍스트 바꾸기
                    binding.labNumber.setText("911")
                    run()
                }
                "916" -> {
                    // 실습실 텍스트 바꾸기
                    binding.labNumber.setText("915")
                    run()
                }
                "918" -> {
                    // 실습실 텍스트 바꾸기
                    binding.labNumber.setText("916")
                    run()
                }
                "911" -> {
                    // 실습실 텍스트 바꾸기
                    binding.labNumber.setText("911")
                    run()
                }
            }
        }

        // 실습실 앞으로 버튼 눌렀을 때
        binding.nextLabNumber.setOnClickListener {
            binding.labFrame.removeView(binding.labFrame)
            when(binding.labNumber.text) {
                "915" -> {
                    // 실습실 텍스트 바꾸기
                    binding.labNumber.setText("916")
                    run()
                }
                "916" -> {
                    // 실습실 텍스트 바꾸기
                    binding.labNumber.setText("918")
                    run()
                }
                "911" -> {
                    // 실습실 텍스트 바꾸기
                    binding.labNumber.setText("915")
                    run()
                }
                "918" -> {
                    // 실습실 텍스트 바꾸기
                    binding.labNumber.setText("918")
                    run()
                }
            }
        }

        // 좌석 클릭했을 때 누가 앉아있는지 다이얼로그로 표시
        // 이거는 리팩토링할 때 구조 다시 생각해서 바꾸기
        binding.seat1.setOnClickListener {
            show_detail_seat(binding.seat1.text.toString())
        }

        binding.seat2.setOnClickListener {
            show_detail_seat(binding.seat2.text.toString())
        }
        binding.seat3.setOnClickListener {
            show_detail_seat(binding.seat3.text.toString())
        }
        binding.seat4.setOnClickListener {
            show_detail_seat(binding.seat4.text.toString())
        }
        binding.seat5.setOnClickListener {
            show_detail_seat(binding.seat5.text.toString())
        }
        binding.seat6.setOnClickListener {
            show_detail_seat(binding.seat6.text.toString())
        }
        binding.seat7.setOnClickListener {
            show_detail_seat(binding.seat7.text.toString())
        }
        binding.seat8.setOnClickListener {
            show_detail_seat(binding.seat8.text.toString())
        }
        binding.seat9.setOnClickListener {
            show_detail_seat(binding.seat9.text.toString())
        }
        binding.seat10.setOnClickListener {
            show_detail_seat(binding.seat10.text.toString())
        }
        binding.seat11.setOnClickListener {
            show_detail_seat(binding.seat11.text.toString())
        }
        binding.seat12.setOnClickListener {
            show_detail_seat(binding.seat12.text.toString())
        }
        binding.seat13.setOnClickListener {
            show_detail_seat(binding.seat13.text.toString())
        }
        binding.seat14.setOnClickListener {
            show_detail_seat(binding.seat14.text.toString())
        }
        binding.seat15.setOnClickListener {
            show_detail_seat(binding.seat15.text.toString())
        }
        binding.seat16.setOnClickListener {
            show_detail_seat(binding.seat16.text.toString())
        }
        binding.seat17.setOnClickListener {
            show_detail_seat(binding.seat17.text.toString())
        }
        binding.seat18.setOnClickListener {
            show_detail_seat(binding.seat18.text.toString())
        }
        binding.seat19.setOnClickListener {
            show_detail_seat(binding.seat19.text.toString())
        }
        binding.seat20.setOnClickListener {
            show_detail_seat(binding.seat20.text.toString())
        }
        binding.seat21.setOnClickListener {
            show_detail_seat(binding.seat21.text.toString())
        }
        binding.seat22.setOnClickListener {
            show_detail_seat(binding.seat22.text.toString())
        }
        binding.seat23.setOnClickListener {
            show_detail_seat(binding.seat23.text.toString())
        }
        binding.seat24.setOnClickListener {
            show_detail_seat(binding.seat24.text.toString())
        }
        binding.seat25.setOnClickListener {
            show_detail_seat(binding.seat25.text.toString())
        }
        binding.seat26.setOnClickListener {
            show_detail_seat(binding.seat26.text.toString())
        }
        binding.seat27.setOnClickListener {
            show_detail_seat(binding.seat27.text.toString())
        }
        binding.seat28.setOnClickListener {
            show_detail_seat(binding.seat28.text.toString())
        }
        binding.seat29.setOnClickListener {
            show_detail_seat(binding.seat29.text.toString())
        }
        binding.seat30.setOnClickListener {
            show_detail_seat(binding.seat30.text.toString())
        }
        binding.seat31.setOnClickListener {
            show_detail_seat(binding.seat31.text.toString())
        }
        binding.seat32.setOnClickListener {
            show_detail_seat(binding.seat32.text.toString())
        }
        binding.seat33.setOnClickListener {
            show_detail_seat(binding.seat33.text.toString())
        }
        binding.seat34.setOnClickListener {
            show_detail_seat(binding.seat34.text.toString())
        }
        binding.seat35.setOnClickListener {
            show_detail_seat(binding.seat35.text.toString())
        }
        binding.seat36.setOnClickListener {
            show_detail_seat(binding.seat36.text.toString())
        }
        binding.seat37.setOnClickListener {
            show_detail_seat(binding.seat37.text.toString())
        }
        binding.seat38.setOnClickListener {
            show_detail_seat(binding.seat38.text.toString())
        }
        binding.seat39.setOnClickListener {
            show_detail_seat(binding.seat39.text.toString())
        }
        binding.seat40.setOnClickListener {
            show_detail_seat(binding.seat40.text.toString())
        }

        // 실습실 예약 버튼을 눌렀을 때
        binding.labReservationBtn.setOnClickListener {
            var intent = Intent(activity, Lab_ReservationActivity::class.java)
            intent.putExtra("key",user)
            startActivity(intent)
        }


        return mBinding?.root
    }

    // 강의실 안 열렸을 때 숨기는 함수
    fun seatHide() {
        for (i: Int in 0..3) {
            binding.labLeftSeat.setColumnCollapsed(i, true)
            binding.labRightSeat.setColumnCollapsed(i, true)
        }

        binding.labLeftSeat.setHeight(0)
        binding.labRightSeat.setHeight(0)
    }

    // 강의실이 열리면 표시하는 함수
    fun seatShow() {
        for (i: Int in 0..3) {
            binding.labLeftSeat.setColumnCollapsed(i, false)
            binding.labRightSeat.setColumnCollapsed(i, false)
            binding.labLeftSeat.setHeight(520)
            binding.labRightSeat.setHeight(520)
        }
    }

    // view height 설정을 위한 함수
    fun View.setHeight(value: Int) {
        val lp = layoutParams
        lp?.let {
            lp.height = value
            layoutParams = lp
        }
    }

    // 좌석 클릭했을 때 사용자 정보 보여주는 함수
    fun show_detail_seat(seatingNum : String){
        response_userList.forEach { it ->
            if(it.seat == seatingNum && it.labNumber == binding.labNumber.text.toString()) {
                val dialog = SeatInfo_Dialog(requireActivity())
                dialog.showDialog(it)
                dialog.setOnClickListener(object : SeatInfo_Dialog.OnDialogClickListener {
                    override fun onClicked() {
                        Toast.makeText(activity, "신고되었습니다.", Toast.LENGTH_SHORT).show()
                    }})
            }
        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    // labNumber에 해당하는 실습실 정보 읽어오기
    fun labStatus(labNumber: String) {
        val TAG: String = "강의실 상태 로그"
        val call = RetrofitBuilder.api_lab.getReservationStatusResponse()

        lab_915.clear(); lab_916.clear(); lab_918.clear(); lab_911.clear()

        Log.d(TAG, "stu_LabStatusFragment - labStatus() called 성공 1")

        call.enqueue(object : Callback<List<TodayReservationDto>> { // 비동기 방식 통신 메소드

            @RequiresApi(Build.VERSION_CODES.S)
            override fun onResponse( // 통신에 성공한 경우
                call: Call<List<TodayReservationDto>>,
                response: Response<List<TodayReservationDto>>
            ) {
                Log.d(TAG, "stu_LabStatusFragment - onResponse() called 성공2")
                if (response.isSuccessful) { // 응답 잘 받은 경우
                    Log.d(TAG, "stu_LabStatusFragment - onResponse() called 성공3")
                    try {
                        //어떻게 응답
                        Log.d(TAG, "stu_LabStatusFragment - onResponse() called 성공4")
                        response_userList = response.body()!!
                        Log.d(TAG, "총 예약되어 있는 좌석은 " + response_userList.size.toString() + "입니다.")

                        if(response_userList.isNotEmpty()) {

                            // 화면켜자마자 좌석 정보 911 915 916 918 좌석 정보 리스트에 저장
                            response_userList.forEach { it ->
                                when(it.labNumber.toString()) {
                                    "915" -> { lab_915.add(it.seat.toString()); lab_915_adminName.add(it.name) }
                                    "916" -> { lab_916.add(it.seat.toString()); lab_916_adminName.add(it.name) }
                                    "918" -> { lab_918.add(it.seat.toString()); lab_918_adminName.add(it.name) }
                                    "911" -> { lab_911.add(it.seat.toString()); lab_911_adminName.add(it.name) }
                                }
                            }

                            Log.d("로그", response_userList.size.toString())

                            val packageName = activity!!.packageName

                            screenClear(packageName)

                            // 그리는걸 함수로 바꾸고 버튼 클릭시 호실을 인자로 해서 그 함수를 호출
                            drawLabStatus(labNumber, packageName)

                            Log.d("테스트로그", "labStatus 초기화 중 lab 915 현재 인원 수 " + lab_915.size.toString())

                        }
                        else
                            Log.d(TAG, "예약된 좌석 정보가 없습니다.")
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("RESPONSE", "FAILURE")
                }
            }

            override fun onFailure(call: Call<List<TodayReservationDto>>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
            }
        })
    }

    // 맨 처음 강의실 현황 상태로 초기화하는 함수
    fun screenClear(packageName: String) {
        for (i: Int in 1..40) {
            val tvId = resources.getIdentifier("seat${i}", "id", packageName)
            requireView().findViewById<TextView>(tvId).setBackgroundColor(Color.parseColor("#2F5596"))
        }
    }
    
    // 각 강의실 현황대로 분배하는 함수
    fun drawLabStatus(labNumber: String, packageName: String) {
        val Tag = "강의실 그리기 호출"
        when(labNumber) {

            "915" -> { draw(lab_915, lab_915_adminName) ; Log.d(Tag, "stu_LabStatusFragment - drawLabStatus()  915 called")}
            "916" -> { draw(lab_916, lab_916_adminName) ; Log.d(Tag, "stu_LabStatusFragment - drawLabStatus()  916 called")}
            "918" -> { draw(lab_918, lab_918_adminName) ; Log.d(Tag, "stu_LabStatusFragment - drawLabStatus()  918 called")}
            "911" -> { draw(lab_911, lab_911_adminName) ; Log.d(Tag, "stu_LabStatusFragment - drawLabStatus()  911 called")}
        }
    }

    // 진짜로 강의실 현황을 그려주는 함수
    fun draw(lab : MutableList<String>, name: MutableList<String>){
        binding.nowNum.text = "현재 수 : " + lab.size.toString() // 현재 강의실 사용자 수

        // 강의실 사용자가 존재한다면
        if (name.size != 0)
            binding.nowAmin.text = "관리자 : " + name[0] // 가장 먼저 예약한 사람의 이름을 관리자 이름으로 함
        else
            binding.nowAmin.text = "관리자 : X"

        for(i: Int in 0..lab.size - 1) {
            val tvId = resources.getIdentifier("seat${lab.get(i)}", "id", requireActivity().packageName)

            Log.d("로그", "tvId : " + tvId.toString())

            requireView()!!.findViewById<TextView>(tvId).setBackgroundColor(Color.parseColor("#808080"))
        }
    }

    // 강의실 인원 체크, 915 -> 916 -> 918 -> 911 순으로 강의실 열고, 강의실 인원 수가 20명 이상이면 다음 강의실 연다.
    fun labCheck(labNumber: String) {

        when(labNumber) {
            "911" -> {
                if (lab_915.size >= 20 && lab_916.size >= 20 && lab_918.size >= 20) {
                    seatShow()
                } else
                    seatHide()
            }
            "918" -> {
                if (lab_915.size >= 20 && lab_916.size >= 20) {
                    seatShow()
                } else
                    seatHide()
            }
            "916" -> {
                if (lab_915.size >= 20) {
                    seatShow()
                } else
                    seatHide()
            }
            else -> seatShow()
        }
    }

    fun run() {
        // 실습실 좌석 값 읽어와서 예약 상태에 따라 그리기
        screenClear((activity as Student_Nav_Activity).packageName)
        drawLabStatus(binding.labNumber.text.toString(), (activity as Student_Nav_Activity).packageName)
        labCheck(binding.labNumber.text.toString())
    }
}