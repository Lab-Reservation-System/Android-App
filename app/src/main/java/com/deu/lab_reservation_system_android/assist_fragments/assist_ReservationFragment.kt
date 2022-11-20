package com.deu.lab_reservation_system_android.assist_fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.activity.show_All_Reservation_Activity
import com.deu.lab_reservation_system_android.adapter.TodayReservationListTableRowAdapter
import com.deu.lab_reservation_system_android.databinding.FragmentAssistReservationBinding
import com.deu.lab_reservation_system_android.model.Reservation
import com.deu.lab_reservation_system_android.model.row_format.today_Reserve_show_format
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class assist_ReservationFragment : Fragment() {

    private var mBinding : FragmentAssistReservationBinding? = null
    var selectingID = "z"
    var old : View? = null
    var old_position : Int = -1

    private lateinit var binding: FragmentAssistReservationBinding
    private lateinit var tableRecyclerView : RecyclerView

    private lateinit var reservationlist_tableRowAdapter: TodayReservationListTableRowAdapter

    lateinit var response_ReservationList : MutableList<Reservation>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("조교로그", "onCreateView: 예약관리")
        binding = FragmentAssistReservationBinding.inflate(inflater, container, false)

        mBinding = binding

        get_reservationList()

        binding.searchAllBtn.setOnClickListener(){
            update_Viewer(0)
        }
        binding.searchAllowBtn.setOnClickListener(){
            update_Viewer(1)
        }
        binding.searchDenyBtn.setOnClickListener(){
            update_Viewer(2)
        }
        binding.showAllBtn.setOnClickListener(){    // 전체 조회시 화면 넘어가기
            var intent = Intent(activity, show_All_Reservation_Activity::class.java)
            startActivity(intent)
        }


        binding.allAllowBtn.setOnClickListener(){
            val builder = AlertDialog.Builder(activity)
                .setTitle("확인!")
                .setMessage("모두 승인하시겠습니까?")
                .setPositiveButton("닫기", DialogInterface.OnClickListener{ dialog, which -> })
                .setNegativeButton("확인", DialogInterface.OnClickListener{ dialog, which -> allow_all_user();Toast.makeText(activity, "승인처리되었습니다.", Toast.LENGTH_SHORT).show()})

            builder.show()

        }
        binding.selectAllowBtn.setOnClickListener(){
            if(selectingID == "z")  //선택을 안했으면
                Toast.makeText(activity, "값을 선택해주세요", Toast.LENGTH_SHORT).show()
            else {
                allow_user(selectingID)
                Toast.makeText(activity, "승인처리되었습니다.", Toast.LENGTH_SHORT).show()
            }


        }
        binding.selectDenyBtn.setOnClickListener(){
            if(selectingID == "z")  //선택을 안했으면
                Toast.makeText(activity, "값을 선택해주세요", Toast.LENGTH_SHORT).show()
            else {
                deny_user(selectingID)
                Toast.makeText(activity, "삭제처리되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }


        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    fun update_Viewer(i:Int){

        var reservationList = ArrayList<today_Reserve_show_format>()
        selectingID = "z"

        if(i==0){  //init 조회
            response_ReservationList?.forEach { it ->
                reservationList.add(today_Reserve_show_format(it.name, it.id,it.labNumber,it.seat,it.time,if(it.permissionState == true) "승인" else "미승인"))
            }
        }
        else if(i==1) {
            response_ReservationList?.forEach { it ->
                if (it.permissionState==true)//키워드가 포함된다면
                    reservationList.add(today_Reserve_show_format(it.name, it.id,it.labNumber,it.seat,it.time,"승인"))
            }
        }
        else{
            response_ReservationList?.forEach { it ->
                if (it.permissionState==false)//키워드가 포함된다면
                    reservationList.add(today_Reserve_show_format(it.name, it.id,it.labNumber,it.seat,it.time,"미승인"))
            }
        }

        tableRecyclerView = binding.tableRecyclerView.findViewById(R.id.table_recycler_view)
        reservationlist_tableRowAdapter = TodayReservationListTableRowAdapter(reservationList)

        tableRecyclerView.layoutManager = LinearLayoutManager(context)
        tableRecyclerView.adapter = reservationlist_tableRowAdapter

        reservationlist_tableRowAdapter.setItemClickListener(object: TodayReservationListTableRowAdapter.OnItemClickListener{
            override fun onClick(v: View, i: Int) {

                if(v != old&& i!= old_position){
                    if(old != null)
                        selected_row(old!!)
                    old_position = i
                    old = v
                }
                Log.d("몇번째클릭", "onClick: ${i} ")
                response_ReservationList.forEach { it ->
                    if(it.id==reservationList.get(i).id &&
                       it.labNumber==reservationList.get(i).labNumber &&
                       it.seat==reservationList.get(i).seat &&
                       it.time==reservationList.get(i).time){
                        selectingID = it.reservationNum
                        Log.d("클릭한 예약번호", "${it.reservationNum}")
                    }
                }
            }
        })


    }
    fun selected_row(v:View){
        v.findViewById<TextView>(R.id.userName).setBackgroundResource(R.drawable.blue_corner)
        v.findViewById<TextView>(R.id.userId).setBackgroundResource(R.drawable.blue_corner)
        v.findViewById<TextView>(R.id.labNum).setBackgroundResource(R.drawable.blue_corner)
        v.findViewById<TextView>(R.id.seatNum).setBackgroundResource(R.drawable.blue_corner)
        v.findViewById<TextView>(R.id.usingTime).setBackgroundResource(R.drawable.blue_corner)
        v.findViewById<TextView>(R.id.status).setBackgroundResource(R.drawable.blue_corner)
    }

    fun get_reservationList() {
        val call = RetrofitBuilder.api_lab.getTodayReservationResponse()
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

//                        Log.d("테스트용",response_ReservationList[0].id)
                        update_Viewer(0)



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

    fun allow_user(res_id:String) {
        val call = RetrofitBuilder.api_lab.getAllowReservation(res_id)
        Log.d("Watching: ", "성공1")
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우

                    try {
                        Log.d("Watching: ", "성공2")

                        response_ReservationList.forEach { it ->
                            if (it.reservationNum == res_id)
                                it.permissionState = true
                        }

                        update_Viewer(0)



                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("Watching: ", "실패1")
                    //wrong()

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "실패2")

            }
        })
    }
    fun allow_all_user() {
        val call = RetrofitBuilder.api_lab.getAllAllowReservation()
        Log.d("Watching: ", "성공1")
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우

                    try {
                        Log.d("Watching: ", "성공2")

                        response_ReservationList.forEach { it ->
                            if (it.permissionState == false)
                                it.permissionState = true
                        }

                        update_Viewer(0)



                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("Watching: ", "실패1")
                    //wrong()

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "실패2")

            }
        })
    }

    fun deny_user(res_id:String) {
        val call = RetrofitBuilder.api_lab.getDenyReservation(res_id)
        var temp : Reservation? = null
        Log.d("Watching: ", "성공1")
        call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우

                    try {
                        Log.d("Watching: ", "성공2")

                        response_ReservationList.forEach { it ->
                            if (it.reservationNum == res_id) {
                                Log.d("삭제전", "onResponse: ${response_ReservationList.size}")
                                temp = it
                            }

                        }
                        Log.d("삭제후", "onResponse: ${response_ReservationList.size}")

                        response_ReservationList.remove(temp)
                        update_Viewer(0)

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("Watching: ", "실패1")
                    //wrong()

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "실패2")

            }
        })
    }


}