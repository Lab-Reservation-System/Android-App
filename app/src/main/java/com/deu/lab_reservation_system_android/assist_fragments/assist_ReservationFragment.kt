package com.deu.lab_reservation_system_android.assist_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deu.lab_reservation_system_android.databinding.FragmentAssistReservationBinding
import com.deu.lab_reservation_system_android.databinding.FragmentStuLabstatusBinding


class assist_ReservationFragment : Fragment() {

    private var mBinding : FragmentAssistReservationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("조교로그", "onCreateView: 예약관리")
        val binding = FragmentAssistReservationBinding.inflate(inflater, container, false)

        mBinding = binding

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}