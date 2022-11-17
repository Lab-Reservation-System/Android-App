package com.deu.lab_reservation_system_android.stu_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deu.lab_reservation_system_android.activity.User.Password_CheckActivity
import com.deu.lab_reservation_system_android.activity.User.LoginActivity
import com.deu.lab_reservation_system_android.nav.Student_Nav_Activity

import com.deu.lab_reservation_system_android.databinding.FragmentStuMypageBinding

class stu_MyPageFragment : Fragment() {

    private var mBinding : FragmentStuMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("로그", "onCreateView:학생 마이페이ㅣㅈ")

        var user = (activity as Student_Nav_Activity).getUser()

        val binding = FragmentStuMypageBinding.inflate(inflater, container, false)
        mBinding = binding

        binding.userId.setText(user.id)
        binding.userEmail.setText(user.email)
        binding.userName.setText(user.name)
        binding.userPhone.setText(user.phoneNumber)

        binding.logoutBtn.setOnClickListener(){
            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.infoChangeBtn.setOnClickListener(){
            var intent = Intent(activity, Password_CheckActivity::class.java)
            intent.putExtra("key",user)
            startActivity(intent)
        }

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}