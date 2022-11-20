package com.deu.lab_reservation_system_android.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
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

class SeminarRegist_Dialog(context : Context) {

    var currentTime : Long = 0

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }

    fun showDialog(user : User) {

        var dateString:String = ""

        currentTime = System.currentTimeMillis()
        val dataFormat = SimpleDateFormat("yyyy-MM-dd")
        val today = dataFormat.format(currentTime)

        Log.d("가져온 날짜", "showDialog: ${today}")
        Log.d("가져온 아이디", "showDialog: ${user.id}")
        dialog.setContentView(R.layout.dialog_regist_seminar)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()


        val date_text = dialog.findViewById<TextView>(R.id.datetext)
        date_text.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}-${month + 1}-${dayOfMonth}"
                var datetemp = dataFormat.parse(dateString)
                dateString = dataFormat.format(datetemp)

                date_text.setText(dateString)
            }
            DatePickerDialog(dialog.context, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // 스피너 선언
        var labSpinner = dialog.findViewById<Spinner>(R.id.labSpinner)
        // 실습실 선택 화면
        var labNumber = dialog.findViewById<EditText>(R.id.labNumber)
        // 어뎁터 설정 - resource - array.xml에 있는 아이템 목록을 추가한다.
        labSpinner.adapter = ArrayAdapter.createFromResource(dialog.context, R.array.labList, android.R.layout.simple_spinner_item)
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

        var seminarName= dialog.findViewById<EditText>(R.id.className)
        // 관리자명
        var profName = dialog.findViewById<EditText>(R.id.professorName)

        val regist_btn = dialog.findViewById<Button>(R.id.registerBtn)




        regist_btn.setOnClickListener() {
            var classes : MutableList<ClassesDto> = mutableListOf()
            Log.d("날짜비교", "showDialog: ${today.compareTo(dateString)}")
            if (seminarName.text.toString().length == 0 ||
                profName.text.toString().length == 0 ||
                sTime.text.toString().length == 0 ||
                eTime.text.toString().length == 0 ||
                dateString.length ==0
            ) {
                val builder = AlertDialog.Builder(dialog.context)
                    .setTitle("오류")
                    .setMessage("모든 내용을 입력하세요")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                builder.show()

            } else {
                if(today.compareTo(dateString) > 0)
                {
                    val builder = AlertDialog.Builder(dialog.context)
                        .setTitle("오류")
                        .setMessage("세미나를 과거에 등록할 수 없습니다")
                        .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                    builder.show()
                }
                else{

                    val TAG = "수업등록 버튼클릭"
                    Log.d(TAG, "showDialog: " + seminarName.text.toString())
                    Log.d(TAG, "showDialog: " + profName.text.toString())
                    Log.d(TAG, "showDialog: " + labNumber.text.toString())
                    Log.d(TAG, "showDialog: " + sTime.text.toString())
                    Log.d(TAG, "showDialog: " + eTime.text.toString())

                    lateinit var st_yoil : String
                    var prof = profName.text.toString()
                    var class_name = seminarName.text.toString()
                    var lab_num = labNumber.text.toString()
                    var startTime = sTime.text.toString().split(':')
                    var endTime = eTime.text.toString().split(':')

                    Log.d(TAG, "showDialog: "+startTime[0]+"/"+endTime[0])

                    var st = startTime[0].toInt()
                    var end = endTime[0].toInt()
                    if(st < end) end = end-1


                    for(t in st..end)
                    {
                        var tempclass : ClassesDto
                        tempclass = ClassesDto(user.id,prof,class_name,lab_num,dateString,t.toString(),"Seminar")
                        classes.add(tempclass)
                    }


                    get_create(classes)

                }
            }
        }

    }
    interface OnDialogClickListener
    {
        fun onClicked(i:Int)
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