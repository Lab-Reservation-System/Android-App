package com.deu.lab_reservation_system_android.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.Dto.ReservationDto
import com.deu.lab_reservation_system_android.model.Reservation
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CanUseSeat_Dialog(context : Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog(labNum:String, reservationTodayList : MutableList<Reservation>, reservationList : MutableList<ReservationDto> )
    {
        dialog.setContentView(R.layout.dialog_can_use_seat)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val lab = dialog.findViewById<TextView>(R.id.labNumber)
        lab.setText(labNum)


        val cancel_btn = dialog.findViewById<Button>(R.id.cancel_btn)

        cancel_btn.setOnClickListener() {
            dialog.dismiss()
        }

        //reservationTodayList - 오늘 예약 정보
        //reservationList - 내 예약 정보
        var Cantseat : MutableSet<Int> = compareWithReserve(reservationTodayList,reservationList)


        // 체크박스 리스트로 바인딩

        var packName : String  = dialog.context.packageName // 패키지 이름
        var Can_seat = arrayOfNulls<TextView>(41)   // 좌석
        for(i in 1..40){
            var resName = "@id/seat"+i.toString()
            var resId = dialog.context.resources.getIdentifier(resName, "id", packName)
            Can_seat[i] = dialog.findViewById(resId)

            if(i in Cantseat)   //못 앉는 좌석 처리
            {
                Can_seat[i]?.setBackgroundColor(Color.parseColor("#808080"))
            }
            else{

                Can_seat[i]?.setOnClickListener{
                    Log.d("좌석 클릭 리스너 : ", "onCreate: ${i}번째 클릭")
                    val builder = AlertDialog.Builder(dialog.context)
                        .setTitle("최종 확인")
                        .setMessage("${i}번 좌석으로 예약하시겠습니까?")
                        .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> settingSeat(reservationList,i)})
                        .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which -> })
                    builder.show()
                }
            }


        }

    }
    fun settingSeat(reservationList : MutableList<ReservationDto>, i : Int){
        reservationList.forEach({ it ->
            it.seat = i.toString() // 해당 좌석으로 모든 예약 내역 저장
        })
        registReservation(reservationList)

    }

    fun compareWithReserve(reservationTodayList : MutableList<Reservation>, reservationList : MutableList<ReservationDto> ): MutableSet<Int> {
        var alreadyExist : MutableSet<Int> = mutableSetOf()
        for(i in reservationList) {
            for (j in reservationTodayList){
                if(i.time == j.time) {
                    alreadyExist.add(j.seat.toInt())
                }

            }
        }
        return alreadyExist

    }

    fun registReservation(reservationList : MutableList<ReservationDto>){
            val call = RetrofitBuilder.api_reservation.registReservationResponse(reservationList)
            Log.d("Watching: ", "성공1")
            call.enqueue(object : Callback<String> { // 비동기 방식 통신 메소드

                override fun onResponse( // 통신에 성공한 경우
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful()) { // 응답 잘 받은 경우
                        //Log.d("RESPONSE: ", response.body().toString())
                        try {
                            Log.d("Watching: ", "성공2")
                            dialog.dismiss()

                        }catch (e: JSONException){
                            e.printStackTrace()
                        }

                    } else {
                        // 통신 성공 but 응답 실패
                        Log.d("Watching: ", "실패1")
                        val builder = AlertDialog.Builder(dialog.context)
                            .setTitle("오류")
                            .setMessage("시간이 중복되었습니다.")
                            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                        builder.show()

                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // 통신에 실패한 경우
                    Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                    Log.d("Watching: ", "실패2")
                }
            })
    }



    interface OnDialogClickListener
    {
        fun onClicked()
    }

}
