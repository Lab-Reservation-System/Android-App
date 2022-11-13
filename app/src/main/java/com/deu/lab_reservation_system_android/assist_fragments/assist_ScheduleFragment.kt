package com.deu.lab_reservation_system_android.assist_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deu.lab_reservation_system_android.databinding.FragmentAssistScheduleBinding
import com.deu.lab_reservation_system_android.databinding.FragmentStuScheduleBinding

class assist_ScheduleFragment : Fragment() {

    private var mBinding : FragmentAssistScheduleBinding? = null

        override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("조교 로그", "onCreateView: 시간표")
        val binding = FragmentAssistScheduleBinding.inflate(inflater, container, false)

        mBinding = binding

        return mBinding?.root
    }

    override fun onDestroyView() {

        mBinding = null
        super.onDestroyView()
    }
}