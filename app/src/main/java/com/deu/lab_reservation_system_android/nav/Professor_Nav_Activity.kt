package com.deu.lab_reservation_system_android.nav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Half
import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.databinding.ActivityProfessorNavBinding
import javax.net.ssl.HostnameVerifier


class Professor_Nav_Activity : AppCompatActivity() {

    private lateinit var mBinding: ActivityProfessorNavBinding

    lateinit var data: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = intent.getSerializableExtra("key") as User

        Log.d("객체 전달", "id: " + data.id + ", pw: " + data.password)

        mBinding = ActivityProfessorNavBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        // 네비게이션들을 담는 호스트
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.prof_nav_host) as NavHostFragment

        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController


        // 바텀 네비게이션 뷰와 네비게이션을 묶어줌
        NavigationUI.setupWithNavController(mBinding.profBottomNav, navController)


    }
    fun getUser(): User {
        return this.data
    }
}