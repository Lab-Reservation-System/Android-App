package com.deu.lab_reservation_system_android.prof_fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deu.lab_reservation_system_android.databinding.FragmentAssistScheduleBinding
import com.deu.lab_reservation_system_android.databinding.FragmentProfScheduleBinding
import com.deu.lab_reservation_system_android.databinding.FragmentStuScheduleBinding
import com.deu.lab_reservation_system_android.dialog.ClassInfo_Dialog
import com.deu.lab_reservation_system_android.dialog.ClassInfo_assistant_Dialog
import com.deu.lab_reservation_system_android.dialog.ClassRegist_Dialog
import com.deu.lab_reservation_system_android.dialog.SeminarRegist_Dialog
import com.deu.lab_reservation_system_android.model.Classes
import com.deu.lab_reservation_system_android.model.Schedule
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.nav.Assistant_Nav_Activity
import com.deu.lab_reservation_system_android.nav.Professor_Nav_Activity
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class prof_ScheduleFragment : Fragment() {

    private var mBinding : FragmentProfScheduleBinding? = null
    var weekset : MutableList<String> = mutableListOf()
    var weekList : MutableList<String> = mutableListOf("", "", "", "", "", "", "") // 일주일 등록됨
    lateinit var classList : List<Classes>
    var weekclassList : MutableList<Classes> = mutableListOf()
    var scheduleList : MutableList<Schedule> = mutableListOf()
    lateinit var user : User
    var clicked : String = "915"

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("조교 로그", "onCreateView: 시간표")

        user = (activity as Professor_Nav_Activity).getUser() // 사용자 정보 가져오기

        var binding = FragmentProfScheduleBinding.inflate(inflater, container, false)
        mBinding = binding


        getWeekSet()    // 일주일치 날짜 가져오기
        getMonthstartDate() // 각 요일에 해당하는 날짜로 매핑
        weekList?.forEach { it ->
            Log.d("요일 확인", it)
        }
        get_ClassesList() //모든 수업 다 가져오기


        val col_set : Array<String> = arrayOf("","월","화","수","목","금")
        val row_set : Array<String> = arrayOf("","1교시","2교시","3교시","4교시","5교시","6교시","7교시","8교시")
        binding.schedule.setColumnNames(col_set)
        binding.schedule.setRowNames(row_set)





        clicked = "915"
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->

            when(i){
                binding.radioButton915.id -> { clicked = "915" }
                binding.radioButton916.id -> { clicked = "916" }
                binding.radioButton918.id  -> { clicked = "918" }
                binding.radioButton911.id -> { clicked = "911" }
            }
            delete_Schedule()
            getMapToScedule(clicked)

        }


        binding.regularRegisterBtn.setOnClickListener() //정규수업 등록
        {
            val dialog = ClassRegist_Dialog(requireActivity())
            dialog.showDialog(user)
            dialog.setOnClickListener(object : ClassRegist_Dialog.OnDialogClickListener {
                override fun onClicked(num : Int) {
                    binding.radioButton915.isChecked = true
                    clicked= "915"
                    delete_Schedule()
                    get_ClassesList() //모든 수업 다 가져오기

                }

            })

        }
        binding.seminarRegisterBtn.setOnClickListener() //특강/세미나 등록
        {
            val dialog = SeminarRegist_Dialog(requireActivity())
            dialog.showDialog(user)
            dialog.setOnClickListener(object : SeminarRegist_Dialog.OnDialogClickListener {
                override fun onClicked(num : Int) {
                    binding.radioButton915.isChecked = true
                    clicked= "915"
                    delete_Schedule()
                    get_ClassesList() //모든 수업 다 가져오기

                }

            })
        }

        return mBinding?.root
    }
    fun getWeekSet(){
        weekset = mutableListOf()
        for(i in 0..6){
            var currentTime = LocalDateTime.now().plusDays(i.toLong());
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            weekset.add(currentTime.format(formatter))
        }
    }
    fun getDOW(whatdate: String?): String { //getDayOfWeek
        var dateFormat = "yyyy-MM-dd"   //날짜 형식
        val cal: Calendar = Calendar.getInstance()
        val df = SimpleDateFormat(dateFormat)
        var dDay = Date()
        try {
            dDay = df.parse(whatdate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        cal.time = dDay
        return cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.KOREAN)
    }

    fun getMonthstartDate() //일주일 받기
    {
        var dayName : String
        weekset?.forEach { it ->
            dayName = getDOW(it)
            when(dayName){
                "월요일" -> weekList[0] = it
                "화요일" -> weekList[1] = it
                "수요일" -> weekList[2] = it
                "목요일" -> weekList[3] = it
                "금요일" -> weekList[4] = it
                "토요일" -> weekList[5] = it
                "일요일" -> weekList[6] = it
            }
        }
    }

    fun get_ClassesList(){
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

                        get_weekClass() //일주일치

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("Watching: ", "실패1")
                    //wrong()

                }
            }

            override fun onFailure(call: Call<List<Classes>>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "실패2")
            }
        })
    }

    fun get_weekClass(){
        weekclassList = mutableListOf()
        classList.forEach({ it ->
            if(it.date in weekList)
                weekclassList.add(it)
        })
        getMapToScedule("915")
    }

    fun delete_Schedule()
    {
        scheduleList.forEach({ it->
            mBinding!!.schedule.deleteSchedule(it.time,it.date)
        } )

    }
    fun getMapToScedule(lab : String)
    {
        scheduleList = mutableListOf()
        weekclassList.forEach({it ->

            if(it.labNumber==lab)
            {
                var timeOf : String = ""
                when(it.time){
                    "9" -> {timeOf="1교시"} "10" -> {timeOf="2교시"}
                    "11" -> {timeOf="3교시"} "12" -> {timeOf="4교시"}
                    "13" -> {timeOf="5교시"} "14" -> {timeOf="6교시"}
                    "15" -> {timeOf="7교시"} "16" -> {timeOf="8교시"}
                }

                var dateOf : String = ""
                when(it.date){
                    weekList[0] -> { dateOf ="월"}
                    weekList[1] -> { dateOf ="화" }
                    weekList[2] -> { dateOf ="수" }
                    weekList[3] -> { dateOf ="목" }
                    weekList[4] -> { dateOf ="금" }
                }


                if(dateOf != "" && timeOf != "") {
                    var schedule_temp = Schedule(
                        it.userId,
                        it.regularClassNum,it.classNum,
                        if (it.className == null) "" else it.className,
                        it.userName,
                        timeOf,
                        dateOf,
                        1
                    )
                    scheduleList.add(schedule_temp!!)
                }
                //"월요일" -> st_yoil = monthList[0]


            }
        })
        scheduleList.forEach({ it->

            var temp = it
            var red = 0
            var blue = 0
            var green = 0

            //랜덤으로 값을 넣는다. 0 ~ 255
            red = (Math.random() * 255).toInt()
            blue = (Math.random() * 255).toInt()
            green = (Math.random() * 255).toInt()

            Log.d("요일체크", "getMapToScedule: "+it.className)
            var C_name = it.className+"-"+it.proName

            if(C_name.length > 6 ){
                var range = IntRange(0,4)
                mBinding!!.schedule.addSchedule(C_name.slice(range)+"..",it.time,it.date,it.count,
                    Color.rgb(red,blue,green))
            }
            else
                mBinding!!.schedule.addSchedule(C_name,it.time,it.date,it.count,
                    Color.rgb(red,blue,green))

            mBinding!!.schedule.findCell(it.time,it.date).setOnClickListener {
                Log.d("클릭진짜", "getMapToScedule: "+C_name)
                val dialog = ClassInfo_Dialog(requireActivity())
                dialog.showDialog(temp,user.id)
                dialog.setOnClickListener(object : ClassInfo_Dialog.OnDialogClickListener {
                    override fun onClicked(num : Int) {

                        mBinding!!.radioButton915.isChecked = true
                        clicked= "915"
                        delete_Schedule()

                        get_ClassesList() //모든 수업 다 가져오기

                    }

                })
            }
        })

    }
    override fun onDestroyView() {

        mBinding = null
        super.onDestroyView()
    }
}