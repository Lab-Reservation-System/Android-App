package com.deu.lab_reservation_system_android.stu_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.deu.lab_reservation_system_android.activity.Student_Nav_Activity

import com.deu.lab_reservation_system_android.databinding.FragmentStuMypageBinding
import com.deu.lab_reservation_system_android.model.User

class stu_MyPageFragment : Fragment() {

    private var mBinding : FragmentStuMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("로그", "onCreateView: 마이페이ㅣㅈ")

        var user = (activity as Student_Nav_Activity).getUser()

        val binding = FragmentStuMypageBinding.inflate(inflater, container, false)
        mBinding = binding

        binding.userId.setText(user.id)
        binding.userEmail.setText(user.email)
        binding.userName.setText(user.name)
        binding.userPhone.setText(user.phoneNumber)

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}