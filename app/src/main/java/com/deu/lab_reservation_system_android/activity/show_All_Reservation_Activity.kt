package com.deu.lab_reservation_system_android.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.deu.lab_reservation_system_android.R
import java.util.*

class show_All_Reservation_Activity : AppCompatActivity() {

    var dateString:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_reservation)

        val date = findViewById<Button>(R.id.date)
        val init = findViewById<Button>(R.id.init)
        val back_btn = findViewById<Button>(R.id.back_btn)
        val date_text = findViewById<TextView>(R.id.datetext)

        date.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                date_text.setText(dateString)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        init.setOnClickListener(){
            date_text.setText("-")
        }

        back_btn.setOnClickListener{
            finish()
        }
    }
}