package com.deu.lab_reservation_system_android.activity.shared.professor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deu.lab_reservation_system_android.R

// 교수 본인이 등록한 정규 수업
class ProfessorMyClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor_my_class)
    }
}