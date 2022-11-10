package com.deu.lab_reservation_system_android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.deu.lab_reservation_system_android.R

// 특강 세미나 등록
class SeminarRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seminar_register)

        // 스피너 선언
        val spinner = findViewById<Spinner>(R.id.labSpinner)
        val labNumber = findViewById<TextView>(R.id.labNumber)

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
    }
}