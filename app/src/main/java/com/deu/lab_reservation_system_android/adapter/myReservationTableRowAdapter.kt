package com.deu.lab_reservation_system_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.myReservation

// 테이블에 동적으로 row를 추가하기 위한 어뎁터
class myReservationTableRowAdapter (private var userArrayList: ArrayList<myReservation>) : RecyclerView.Adapter<myReservationTableRowAdapter.ViewHolder> () {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.table_row_layout_reservation_history, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.checkBox.text = userArrayList[i].checkBox.toString()
        viewHolder.UserSeatNum.text = userArrayList[i].seat
        viewHolder.Time.text = userArrayList[i].time
        viewHolder.checkd.text = userArrayList[i].check.toString()
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        val UserSeatNum: TextView = itemView.findViewById(R.id.seatNum)
        val Time: TextView = itemView.findViewById(R.id.time)
        val checkd: TextView = itemView.findViewById(R.id.userCheck)
    }
}