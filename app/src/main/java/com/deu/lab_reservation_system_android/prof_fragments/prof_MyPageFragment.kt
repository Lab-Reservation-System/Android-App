package com.deu.lab_reservation_system_android.prof_fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deu.lab_reservation_system_android.activity.Access_TokenActivity
import com.deu.lab_reservation_system_android.activity.User.LoginActivity
import com.deu.lab_reservation_system_android.activity.nav.Professor_Nav_Activity
import com.deu.lab_reservation_system_android.databinding.FragmentProfMypageBinding

class prof_MyPageFragment : Fragment() {

    private var mBinding : FragmentProfMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("로그", "onCreateView: 마이페이ㅣㅈ")

        var user = (activity as Professor_Nav_Activity).getUser()

        val binding = FragmentProfMypageBinding.inflate(inflater, container, false)
        mBinding = binding

        binding.userId.setText(user.id)
        binding.userEmail.setText(user.email)
        binding.userName.setText(user.name)
        binding.userPhone.setText(user.phoneNumber)

        binding.logoutBtn.setOnClickListener(){
            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}