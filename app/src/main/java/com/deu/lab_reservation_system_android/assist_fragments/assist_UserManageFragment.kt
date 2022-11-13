package com.deu.lab_reservation_system_android.assist_fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.activity.User.StudentUser
import com.deu.lab_reservation_system_android.activity.User.TableRowAdapter
import com.deu.lab_reservation_system_android.databinding.FragmentAssistUsermanageBinding
import com.deu.lab_reservation_system_android.databinding.FragmentStuLabstatusBinding


class assist_UserManageFragment : Fragment() {

    private var mBinding : FragmentAssistUsermanageBinding? = null

    private lateinit var tableRecyclerView : RecyclerView
    private var userList = ArrayList<StudentUser>()
    private lateinit var tableRowAdapter: TableRowAdapter
    private lateinit var user : StudentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("조교 로그", "onCreateView: 사용자 관리")
        val binding = FragmentAssistUsermanageBinding.inflate(inflater, container, false)
        mBinding = binding

        userList.add(StudentUser("정민수", "20183152", "1234", true))
        userList.add(StudentUser("asdasd", "20183152", "1234", true))


        tableRecyclerView = binding.tableRecyclerView.findViewById(R.id.table_recycler_view)
        tableRowAdapter = TableRowAdapter(userList)

        tableRecyclerView.layoutManager = LinearLayoutManager(context)
        tableRecyclerView.adapter = tableRowAdapter





        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}