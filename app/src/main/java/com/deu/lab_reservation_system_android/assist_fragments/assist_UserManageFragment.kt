package com.deu.lab_reservation_system_android.assist_fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.user_show_format
import com.deu.lab_reservation_system_android.adapter.UserListTableRowAdapter
import com.deu.lab_reservation_system_android.databinding.FragmentAssistUsermanageBinding
import com.deu.lab_reservation_system_android.dialog.BlackList_Dialog
import com.deu.lab_reservation_system_android.dialog.ProfSignUp_Dialog
import com.deu.lab_reservation_system_android.dialog.UserEdit_Dialog
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class assist_UserManageFragment : Fragment() {

    private var mBinding : FragmentAssistUsermanageBinding? = null

    private lateinit var tableRecyclerView : RecyclerView

    private lateinit var userListTableRowAdapter: UserListTableRowAdapter
    private lateinit var binding: FragmentAssistUsermanageBinding
    lateinit var response_userList:List<User>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("조교 로그", "onCreateView: 사용자 관리")
        binding = FragmentAssistUsermanageBinding.inflate(inflater, container, false)
        mBinding = binding

        get_all_user()

        binding.userSearchBtn.setOnClickListener(){
            var keyword : String = binding.userSearchText.text.toString()
            update_Viewer(keyword)
        }
        binding.professorRegister.setOnClickListener(){
            signup_prof()
        }
        binding.blackListBtn.setOnClickListener()
        {
            val dialog = BlackList_Dialog(requireActivity())
            dialog.showDialog()
        }


        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }



    fun update_Viewer(keyword:String){

        var userList = ArrayList<user_show_format>()


        if(keyword.length==0){  //전체조회
            response_userList?.forEach { it ->
                userList.add(user_show_format(it.name, it.id, it.job,if(it.permissionState == true) "승인" else "미승인"))
            }
        }
        else {
            response_userList?.forEach { it ->
                if (it.id.contains(keyword) || it.name.contains(keyword) || it.job.contains(keyword))//키워드가 포함된다면
                    userList.add(user_show_format(it.name, it.id, it.job,if(it.permissionState == true) "승인" else "미승인"))
            }
        }

        tableRecyclerView = binding.tableRecyclerView.findViewById(R.id.table_recycler_view)
        userListTableRowAdapter = UserListTableRowAdapter(userList)

        tableRecyclerView.layoutManager = LinearLayoutManager(context)
        tableRecyclerView.adapter = userListTableRowAdapter

        userListTableRowAdapter.setItemClickListener(object: UserListTableRowAdapter.OnItemClickListener{
            override fun onClick(v: View, i: Int) {
                // 클릭 시 이벤트 작성
                Log.d("몇번째클릭", "onClick: ${i}")
                val dialog = UserEdit_Dialog(activity!!)

                response_userList?.forEach { it ->
                    if(it.id == userList.get(i).stdNum) {
                        dialog.showDialog(it)
                        dialog.setOnClickListener(object : UserEdit_Dialog.OnDialogClickListener {
                            override fun onClicked(ch_num : Int) {
                                Log.d("업데이트뷰어", "onClicked: 클릭됨")
                                if (ch_num == 0) {
                                    Toast.makeText(activity, "변경 완료", Toast.LENGTH_SHORT).show()
                                    update_Viewer("")
                                } else{
                                    Toast.makeText(activity, "삭제 처리중", Toast.LENGTH_SHORT).show()
                                    Handler(Looper.getMainLooper()).postDelayed({ get_all_user() }, 500)
                                }
                            }

                        })

                    }

                }

            }
        })
    }

    fun signup_prof()
    {
        val dialog = ProfSignUp_Dialog(requireActivity())
        dialog.showDialog()
        dialog.setOnClickListener(object : ProfSignUp_Dialog.OnDialogClickListener {
            override fun onClicked(ch_num : Int) {
                //Log.d("업데이트뷰어", "onClicked: 클릭됨")
                if (ch_num == 0) {
                    Toast.makeText(activity, "이미 존재하는 아이디 입니다!", Toast.LENGTH_SHORT).show()
                    //update_Viewer("")
                } else {
                    Toast.makeText(activity, "가입되었습니다.", Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({ get_all_user() }, 1000)
                }
            }})
    }




    fun get_all_user() {
        val call = RetrofitBuilder.api_user.getAllUserResponse()
        Log.d("Watching: ", "성공1")
        call.enqueue(object : Callback<List<User>> { // 비동기 방식 통신 메소드

            override fun onResponse( // 통신에 성공한 경우
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                if (response.isSuccessful()) { // 응답 잘 받은 경우
                    //Log.d("RESPONSE: ", response.body().toString())
                    try {
                        Log.d("Watching: ", "성공2")

                        response_userList = response.body()!!
                        //
                        update_Viewer("")


                    }catch (e:JSONException){
                        e.printStackTrace()
                    }

                } else {
                    // 통신 성공 but 응답 실패
                    Log.d("Watching: ", "실패1")
                    //wrong()

                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // 통신에 실패한 경우
                Log.d("CONNECTION FAILURE: ", t.localizedMessage)
                Log.d("Watching: ", "실패2")
            }
        })
    }


}