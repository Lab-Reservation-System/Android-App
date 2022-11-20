package com.deu.lab_reservation_system_android.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.Dto.ClassesDto
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ClassRegist_Dialog(context : Context) {
    var dateFormat = "yyyy-MM-dd"   //날짜 형식
    var currentTime : Long = 0
    var monthList : MutableList<String> = mutableListOf("", "", "", "", "", "", "")

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog(user : User) {

        currentTime = System.currentTimeMillis()
        val month_dataFormat = SimpleDateFormat("MM")
        val todayMonth = month_dataFormat.format(currentTime).toInt()

        if(todayMonth >= 9 || todayMonth < 3)
            getMonthstartDate("9")
        else
            getMonthstartDate("3")


        //Log.d("가져온 날짜", "showDialog: ${}월")


        Log.d("가져온 아이디", "showDialog: ${user.id}")
        dialog.setContentView(R.layout.dialog_regist_classes)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        // 스피너 선언
        var labSpinner = dialog.findViewById<Spinner>(R.id.labSpinner)

        var daySpinner = dialog.findViewById<Spinner>(R.id.daySpinner)

        // 실습실 선택 화면
        var labNumber = dialog.findViewById<EditText>(R.id.labNumber)

        var yoil = dialog.findViewById<EditText>(R.id.yoil)

        // 어뎁터 설정 - resource - array.xml에 있는 아이템 목록을 추가한다.
        labSpinner.adapter = ArrayAdapter.createFromResource(dialog.context, R.array.labList, android.R.layout.simple_spinner_item)
        daySpinner.adapter = ArrayAdapter.createFromResource(dialog.context, R.array.dayList, android.R.layout.simple_spinner_item)

        // 아이템 선택 리스너
        labSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    // 915
                    0 -> {labNumber.setText(labSpinner.selectedItem.toString())}
                    // 916
                    1 -> {labNumber.setText(labSpinner.selectedItem.toString())}
                    // 918
                    2 -> {labNumber.setText(labSpinner.selectedItem.toString())}
                    // 911
                    3 -> {labNumber.setText(labSpinner.selectedItem.toString())}
                }
            }
        }
        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    // 월
                    0 -> {yoil.setText(daySpinner.selectedItem.toString())}
                    // 화
                    1 -> {yoil.setText(daySpinner.selectedItem.toString())}
                    // 수
                    2 -> {yoil.setText(daySpinner.selectedItem.toString())}
                    // 목
                    3 -> {yoil.setText(daySpinner.selectedItem.toString())}
                    // 금
                    4 -> {yoil.setText(daySpinner.selectedItem.toString())}
//                    // 토
//                    5 -> {yoil.setText(daySpinner.selectedItem.toString())}
//                    // 일
//                    6 -> {yoil.setText(daySpinner.selectedItem.toString())}
                }
            }
        }
        val cancel_btn = dialog.findViewById<Button>(R.id.registerCancleBtn)

        cancel_btn.setOnClickListener() {
            dialog.dismiss()
        }

        // 정규 수업 시작 시간 입력
        var sTime = dialog.findViewById<TextView>(R.id.sTime)
        var selectNum: Int = 0  //시작시간
        var eTime = dialog.findViewById<TextView>(R.id.eTime)
        var eSelectNum: Int = 0 // 종료시간


        sTime.setOnClickListener {
            Log.d("", "showDialog: start 클릭")
            // NumberPicker 화면으로 전환

            val layout = dialog.layoutInflater.inflate(R.layout.dialog_num_select, null)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout)
            }

            val dialog = build.create()
            dialog.show()

            var number_picker = layout.findViewById<NumberPicker>(R.id.number_picker)
            number_picker.minValue = 9
            number_picker.maxValue = 16
            if(selectNum != 0) number_picker.value = selectNum

            var cancelBtn = layout.findViewById<Button>(R.id.cancelBtn)
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            var okBtn = layout.findViewById<Button>(R.id.okBtn)
            okBtn.setOnClickListener {
                selectNum = number_picker.value
                sTime.text = "$selectNum:00"
                eSelectNum = 0
                eTime.text = ""
                dialog.dismiss()
            }
        }

        // 정규 수업 종료 시간 입력

        eTime.setOnClickListener {
            Log.d("", "showDialog: end 클릭")
            if(selectNum != 0) {
                // NumberPicker 화면으로 전환
                val layout = dialog.layoutInflater.inflate(R.layout.dialog_num_select, null)
                val build = AlertDialog.Builder(it.context).apply {
                    setView(layout)
                }

                val dialog = build.create()
                dialog.show()

                var number_picker = layout.findViewById<NumberPicker>(R.id.number_picker)
                number_picker.minValue = selectNum + 1
                number_picker.maxValue = 17
                if (eSelectNum != 0) number_picker.value = selectNum

                var cancelBtn = layout.findViewById<Button>(R.id.cancelBtn)
                cancelBtn.setOnClickListener {
                    dialog.dismiss()
                }

                var okBtn = layout.findViewById<Button>(R.id.okBtn)
                okBtn.setOnClickListener {
                    selectNum = number_picker.value
                    eTime.text = "$selectNum:00"
                    dialog.dismiss()
                }
            }
        }

        // 수업명

        var className= dialog.findViewById<EditText>(R.id.className)
        // 교수명
        var profName= dialog.findViewById<EditText>(R.id.professorName)
        profName.setText(user.name)

        val regist_btn = dialog.findViewById<Button>(R.id.registerBtn)




        regist_btn.setOnClickListener() {
            var classes : MutableList<ClassesDto> = mutableListOf()
            if (className.text.toString().length == 0 ||
                profName.text.toString().length == 0 ||
                sTime.text.toString().length == 0 ||
                eTime.text.toString().length == 0
            ) {
                val builder = AlertDialog.Builder(dialog.context)
                    .setTitle("오류")
                    .setMessage("모든 내용을 입력하세요")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                builder.show()

            } else {
                val TAG = "수업등록 버튼클릭"
                Log.d(TAG, "showDialog: " + className.text.toString())
                Log.d(TAG, "showDialog: " + profName.text.toString())
                Log.d(TAG, "showDialog: " + labNumber.text.toString())
                Log.d(TAG, "showDialog: " + yoil.text.toString())
                Log.d(TAG, "showDialog: " + sTime.text.toString())
                Log.d(TAG, "showDialog: " + eTime.text.toString())

                lateinit var st_yoil : String
                var prof = profName.text.toString()
                var class_name = className.text.toString()
                var lab_num = labNumber.text.toString()
                var startTime = sTime.text.toString().split(':')
                var endTime = eTime.text.toString().split(':')

                Log.d(TAG, "showDialog: "+startTime[0]+"/"+endTime[0])

                var st = startTime[0].toInt()
                var end = endTime[0].toInt()
                if(st < end) end = end-1

                when(yoil.text.toString()){ // 첫 번째 요일
                    "월요일" -> st_yoil = monthList[0]
                    "화요일" -> st_yoil = monthList[1]
                    "수요일" -> st_yoil = monthList[2]
                    "목요일" -> st_yoil = monthList[3]
                    "금요일" -> st_yoil = monthList[4]
                    "토요일" -> st_yoil = monthList[5]
                    "일요일" -> st_yoil = monthList[6]
                }

                for(t in st..end)
                {
                    var tempclass : ClassesDto
                    tempclass = ClassesDto(user.id,prof,class_name,lab_num,st_yoil,t.toString(),"Regular")
                    classes.add(tempclass)
                }


                get_create(classes)


            }
        }

    }
    interface OnDialogClickListener
    {
        fun onClicked(i:Int)
    }


    fun getDOW(whatdate: String?): String { //getDayOfWeek
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
    fun getMonthstartDate(month : String) //학기 시작 월 받기
    {
        var year_dataFormat = SimpleDateFormat("yyyy")
        var todayYear = year_dataFormat.format(currentTime)
        var dayName : String
        for(i in 1..7) {
            var stringtemp : String = i.toString()
            var stringDate : String = todayYear + "-0"+month+"-0"+stringtemp
            dayName = getDOW(stringDate)
            //Log.d(month+"월"+i+"일은", dayName)
            when(dayName){
                "월요일" -> monthList[0] = stringDate
                "화요일" -> monthList[1] = stringDate
                "수요일" -> monthList[2] = stringDate
                "목요일" -> monthList[3] = stringDate
                "금요일" -> monthList[4] = stringDate
                "토요일" -> monthList[5] = stringDate
                "일요일" -> monthList[6] = stringDate
            }
        }

    }

    fun get_create(classes : MutableList<ClassesDto>) {
        val call = RetrofitBuilder.api_classes.getCreateClassResponse(classes)
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
                        onClickListener.onClicked(1)
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
}