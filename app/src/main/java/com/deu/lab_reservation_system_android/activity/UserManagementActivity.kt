package com.deu.lab_reservation_system_android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import com.deu.lab_reservation_system_android.R

// 사용자 관리
class UserManagementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)

        var tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        var tableRow: TableRow
    }
}