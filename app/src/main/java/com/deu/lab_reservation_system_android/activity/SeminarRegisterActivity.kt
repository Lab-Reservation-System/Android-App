package com.deu.lab_reservation_system_android.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.deu.lab_reservation_system_android.R
import java.util.Calendar

// 특강 세미나 등록
class SeminarRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seminar_register)

        // 스피너 선언
        var spinner = findViewById<Spinner>(R.id.labSpinner)

        var labNumber = findViewById<TextView>(R.id.labNumber)

        // 어뎁터 설정 - resource - array.xml에 있는 아이템 목록을 추가한다.
        spinner.adapter = ArrayAdapter.createFromResource(this, R.array.labList, android.R.layout.simple_spinner_item)
        // 아이템 선택 리스너
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    // 915
                    0 -> {
                        labNumber.setText(spinner.selectedItem.toString())
                    }
                    // 916
                    1 -> {
                        labNumber.setText(spinner.selectedItem.toString())
                    }
                    // 918
                    2 -> {
                        labNumber.setText(spinner.selectedItem.toString())
                    }
                }
            }
        }

        // 날짜를 출력하는 TextView에 오늘 날짜 설정
        var seminarDate = findViewById<TextView>(R.id.calendarDate)
        var cal_temp = Calendar.getInstance();
        seminarDate.text = cal_temp.get(Calendar.YEAR).toString() + "년" + (cal_temp.get(Calendar.MONTH) + 1).toString() + "월-" + cal_temp.get(Calendar.DATE) + "일"

        // 세미나 날짜(요일) TextView 클릭 시 캘린더 띄어주기
        seminarDate.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                seminarDate.text = "${year}년 ${month+1}월 ${dayOfMonth}일"
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }


    }
}