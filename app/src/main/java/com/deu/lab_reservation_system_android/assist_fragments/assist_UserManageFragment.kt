package com.deu.lab_reservation_system_android.assist_fragments

import android.os.Bundle
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
import com.deu.lab_reservation_system_android.activity.User.TableRowAdapter
import com.deu.lab_reservation_system_android.databinding.FragmentAssistUsermanageBinding
import com.deu.lab_reservation_system_android.model.User
import com.deu.lab_reservation_system_android.retrofit.RetrofitBuilder
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class assist_UserManageFragment : Fragment() {

    private var mBinding : FragmentAssistUsermanageBinding? = null

    private lateinit var tableRecyclerView : RecyclerView

    private lateinit var tableRowAdapter: TableRowAdapter
    private lateinit var user : user_show_format
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
                userList.add(user_show_format(it.name, it.id, it.job,it.permissionState))
            }
        }
        else {
            response_userList?.forEach { it ->
                if (it.id.contains(keyword) || it.name.contains(keyword) || it.job.contains(keyword))//키워드가 포함된다면
                    userList.add(user_show_format(it.name, it.id, it.job, it.permissionState))
            }
        }

        tableRecyclerView = binding.tableRecyclerView.findViewById(R.id.table_recycler_view)
        tableRowAdapter = TableRowAdapter(userList)

        tableRecyclerView.layoutManager = LinearLayoutManager(context)
        tableRecyclerView.adapter = tableRowAdapter

        tableRowAdapter.setItemClickListener(object: TableRowAdapter.OnItemClickListener{
            override fun onClick(v: View, i: Int) {
                // 클릭 시 이벤트 작성
                Log.d("몇번째클릭", "onClick: ${i}")

            }
        })
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
    fun json_to_array(jsonString : String){
        try {
            val userInfo = JSONObject(jsonString)
            val jsonArray = userInfo.optJSONArray("test")

            Log.d("길이출력먼저", "json_to_array: " + jsonArray.length())
            var i = 0
            var tempStr = ""
            while (i < jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val userName = jsonObject.getString("name")
                val userId = jsonObject.getString("id")

                tempStr = "이름: $userName 아이디: $userId "
                Log.d("출력", "json_to_array: "+ tempStr)
                i++
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


}