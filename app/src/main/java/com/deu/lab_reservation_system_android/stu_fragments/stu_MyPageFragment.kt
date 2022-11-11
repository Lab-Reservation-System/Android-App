package com.deu.lab_reservation_system_android.stu_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.deu.lab_reservation_system_android.databinding.FragmentStuMypageBinding

class stu_MyPageFragment : Fragment() {

    private var mBinding : FragmentStuMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("로그", "onCreateView: 마이페이ㅣㅈ")
        val binding = FragmentStuMypageBinding.inflate(inflater, container, false)

        mBinding = binding

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}