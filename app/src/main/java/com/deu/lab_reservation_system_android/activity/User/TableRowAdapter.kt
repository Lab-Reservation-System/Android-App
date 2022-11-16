package com.deu.lab_reservation_system_android.activity.User

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.user_show_format

class TableRowAdapter (private var userArrayList: ArrayList<user_show_format>) : RecyclerView.Adapter<TableRowAdapter.ViewHolder> () {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_management_table_row_layout, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.UserName.text = userArrayList[i].name
        viewHolder.UserNum.text = userArrayList[i].stdNum
        viewHolder.UserPw.text = userArrayList[i].job
        viewHolder.UserCheck.text = userArrayList[i].check

        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        viewHolder.itemView.setOnClickListener {
            itemClickListener.onClick(it, i)
        }
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val UserName: TextView = itemView.findViewById(R.id.userName)
        val UserNum: TextView = itemView.findViewById(R.id.userNum)
        val UserPw: TextView = itemView.findViewById(R.id.userPw)
        val UserCheck: TextView = itemView.findViewById(R.id.userCheck)
    }

    interface OnItemClickListener {
        fun onClick(v: View, i: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }


    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener


}