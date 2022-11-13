package com.deu.lab_reservation_system_android.assist_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deu.lab_reservation_system_android.activity.nav.Assistant_Nav_Activity
import com.deu.lab_reservation_system_android.activity.nav.Student_Nav_Activity
import com.deu.lab_reservation_system_android.databinding.FragmentAssistMypageBinding

import com.deu.lab_reservation_system_android.databinding.FragmentStuMypageBinding

class assist_MyPageFragment : Fragment() {

    private var mBinding : FragmentAssistMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("조교로그", "onCreateView: 마이페이ㅣㅈ")

        var user = (activity as Assistant_Nav_Activity).getUser()

        val binding = FragmentAssistMypageBinding.inflate(inflater, container, false)
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