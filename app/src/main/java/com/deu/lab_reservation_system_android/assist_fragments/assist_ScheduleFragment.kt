package com.deu.lab_reservation_system_android.assist_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deu.lab_reservation_system_android.databinding.FragmentAssistScheduleBinding
import com.deu.lab_reservation_system_android.databinding.FragmentAssistUsermanageBinding
import com.deu.lab_reservation_system_android.databinding.FragmentStuScheduleBinding
import com.deu.lab_reservation_system_android.dialog.BlackList_Dialog
import com.deu.lab_reservation_system_android.dialog.ClassRegist_Dialog
import com.deu.lab_reservation_system_android.dialog.SeminarRegist_Dialog
import com.deu.lab_reservation_system_android.nav.Assistant_Nav_Activity

class assist_ScheduleFragment : Fragment() {

    private var mBinding : FragmentAssistScheduleBinding? = null


        override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("조교 로그", "onCreateView: 시간표")

        var user = (activity as Assistant_Nav_Activity).getUser() // 사용자 정보 가져오기

        var binding = FragmentAssistScheduleBinding.inflate(inflater, container, false)
        mBinding = binding

        val col : Array<String> = arrayOf("","월","화","수","목","금")
        val row : Array<String> = arrayOf("","1교시","2교시","3교시","4교시","5교시","6교시","7교시","8교시")
        binding.schedule.setColumnNames(col)
        binding.schedule.setRowNames(row)

        binding.radioButton915.setOnClickListener(){
            Log.d("라디오 로그", "onCreateView: 915")
        }

        binding.radioButton916.setOnClickListener(){
            Log.d("라디오 로그", "onCreateView: 916")
        }
        binding.radioButton918.setOnClickListener(){
            Log.d("라디오 로그", "onCreateView: 918")
        }

        binding.radioButton911.setOnClickListener(){
            Log.d("라디오 로그", "onCreateView: 911")
        }

            binding.regularRegisterBtn.setOnClickListener() //정규수업 등록
        {
           val dialog = ClassRegist_Dialog(requireActivity())
           dialog.showDialog(user)
        }
        binding.seminarRegisterBtn.setOnClickListener() //특강/세미나 등록
        {
            val dialog = SeminarRegist_Dialog(requireActivity())
            dialog.showDialog(user)
        }

        return mBinding?.root
    }

    override fun onDestroyView() {

        mBinding = null
        super.onDestroyView()
    }
}