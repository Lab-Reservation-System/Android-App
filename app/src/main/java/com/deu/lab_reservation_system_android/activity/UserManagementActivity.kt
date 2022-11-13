package com.deu.lab_reservation_system_android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.activity.User.StudentUser
import com.deu.lab_reservation_system_android.activity.User.TableRowAdapter

// 사용자 관리
class UserManagementActivity : AppCompatActivity() {

    private lateinit var tableRecyclerView : RecyclerView
    private var userList = ArrayList<StudentUser>()
    private lateinit var tableRowAdapter: TableRowAdapter
    private lateinit var user : StudentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)

        // TableLayout에 row를 동적 추가
        userList.add(StudentUser("정민수", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("정민수", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))


        tableRecyclerView = findViewById(R.id.table_recycler_view)
        tableRowAdapter = TableRowAdapter(userList)

        tableRecyclerView.layoutManager = LinearLayoutManager(this)
        tableRecyclerView.adapter = tableRowAdapter

    }
}