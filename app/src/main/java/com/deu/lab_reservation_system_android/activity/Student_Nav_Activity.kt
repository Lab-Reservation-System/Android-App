package com.deu.lab_reservation_system_android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.databinding.ActivityStudentNavBinding


class Student_Nav_Activity : AppCompatActivity() {

    private lateinit var mBinding: ActivityStudentNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityStudentNavBinding.inflate(layoutInflater)

        val TAG = "시발"
        Log.d(TAG, "1번")
        setContentView(mBinding.root)
        Log.d(TAG, "2번")
        // 네비게이션들을 담는 호스트
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.stu_nav_host) as NavHostFragment

        Log.d(TAG, "3번")
        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController

        // 바텀 네비게이션 뷰와 네비게이션을 묶어줌
        NavigationUI.setupWithNavController(mBinding.stuBottomNav, navController)

    }
}