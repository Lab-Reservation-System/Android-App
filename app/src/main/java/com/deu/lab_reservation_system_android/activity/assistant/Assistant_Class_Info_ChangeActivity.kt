package com.deu.lab_reservation_system_android.activity.assistant

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.deu.lab_reservation_system_android.R

// 조교 : 등록된 정규 수업 정보 수정
class Assistant_Class_Info_ChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assistant_class_info_change)

        // 스피너 선언
        var labSpinner = findViewById<Spinner>(R.id.labSpinner)
        var daySpinner = findViewById<Spinner>(R.id.daySpinner)

        // 실습실 선택 화면
        var labNumber = findViewById<TextView>(R.id.labNumber)

        // 요일 선택 화면
        var yoil = findViewById<TextView>(R.id.yoil)


        // 어뎁터 설정 - resource - array.xml에 있는 아이템 목록을 추가한다.
        labSpinner.adapter = ArrayAdapter.createFromResource(this, R.array.labList, android.R.layout.simple_spinner_item)
        daySpinner.adapter = ArrayAdapter.createFromResource(this, R.array.dayList, android.R.layout.simple_spinner_item)

        // 실습실 아이템 선택 리스너
        labSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                        labNumber.setText(labSpinner.selectedItem.toString())
                    }
                    // 916
                    1 -> {
                        labNumber.setText(labSpinner.selectedItem.toString())
                    }
                    // 918
                    2 -> {
                        labNumber.setText(labSpinner.selectedItem.toString())
                    }
                }
            }
        }

        // 요일 아이템 선택 리스너
        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    // 월
                    0 -> {
                        yoil.setText(daySpinner.selectedItem.toString())
                    }
                    // 화
                    1 -> {
                        yoil.setText(daySpinner.selectedItem.toString())
                    }
                    // 수
                    2 -> {
                        yoil.setText(daySpinner.selectedItem.toString())
                    }
                    // 목
                    3 -> {
                        yoil.setText(daySpinner.selectedItem.toString())
                    }
                    // 금
                    4 -> {
                        yoil.setText(daySpinner.selectedItem.toString())
                    }
                    // 토
                    5 -> {
                        yoil.setText(daySpinner.selectedItem.toString())
                    }
                    // 일
                    6 -> {
                        yoil.setText(daySpinner.selectedItem.toString())
                    }
                }
            }
        }

        // 정규 수업 시작 시간 입력
        var sTime = findViewById<TextView>(R.id.sTime)
        var selectNum: Int = 0

        sTime.setOnClickListener {
            // NumberPicker 화면으로 전환
            val layout = layoutInflater.inflate(R.layout.dialog_num_select, null)
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
                sTime.text = "$selectNum :00"
                dialog.dismiss()
            }
        }

        // 정규 수업 종료 시간 입력
        var eTime = findViewById<TextView>(R.id.eTime)
        var eSelectNum: Int = 0
        eTime.setOnClickListener {
            // NumberPicker 화면으로 전환
            val layout = layoutInflater.inflate(R.layout.dialog_num_select, null)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout)
            }

            val dialog = build.create()
            dialog.show()

            var number_picker = layout.findViewById<NumberPicker>(R.id.number_picker)
            number_picker.minValue = selectNum + 1
            number_picker.maxValue = 17
            if(eSelectNum != 0) number_picker.value = selectNum

            var cancelBtn = layout.findViewById<Button>(R.id.cancelBtn)
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            var okBtn = layout.findViewById<Button>(R.id.okBtn)
            okBtn.setOnClickListener {
                selectNum = number_picker.value
                eTime.text = "$selectNum :00"
                dialog.dismiss()
            }
        }
    }
}