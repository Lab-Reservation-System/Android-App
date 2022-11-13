package com.deu.lab_reservation_system_android.activity.User

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.deu.lab_reservation_system_android.R

class TableRowAdapter (private var userArrayList: ArrayList<StudentUser>) : RecyclerView.Adapter<TableRowAdapter.ViewHolder> () {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_management_table_row_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.UserName.text = userArrayList[i].name
        viewHolder.UserNum.text = userArrayList[i].stdNum
        viewHolder.UserPw.text = userArrayList[i].pwd
        viewHolder.UserCheck.text = userArrayList[i].check.toString()
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
}