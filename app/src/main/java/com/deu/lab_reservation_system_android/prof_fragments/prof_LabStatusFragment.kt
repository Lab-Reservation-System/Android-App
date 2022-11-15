package com.deu.lab_reservation_system_android.prof_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deu.lab_reservation_system_android.databinding.FragmentProfLabstatusBinding
import com.deu.lab_reservation_system_android.databinding.FragmentStuLabstatusBinding


class prof_LabStatusFragment : Fragment() {

    private var mBinding : FragmentProfLabstatusBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("로그", "onCreateView: 실습실현황")
        val binding = FragmentProfLabstatusBinding.inflate(inflater, container, false)

        mBinding = binding

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}