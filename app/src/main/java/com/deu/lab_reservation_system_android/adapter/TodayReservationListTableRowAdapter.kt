package com.deu.lab_reservation_system_android.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.row_format.today_Reserve_show_format
import java.util.ArrayList

class TodayReservationListTableRowAdapter(private var todayreservationListArrayList: ArrayList<today_Reserve_show_format>) : RecyclerView.Adapter<TodayReservationListTableRowAdapter.ViewHolder> () {

    var old = -1
    var selected = -1

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.table_row_layout_today_reservation_list, viewGroup, false) //
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {


        viewHolder.name.text = todayreservationListArrayList[i].name
        viewHolder.id.text = todayreservationListArrayList[i].id
        viewHolder.lab.text = todayreservationListArrayList[i].labNumber
        viewHolder.seat.text = todayreservationListArrayList[i].seat
        viewHolder.time.text = todayreservationListArrayList[i].time
        viewHolder.status.text = todayreservationListArrayList[i].permissionState

        //화면 선택시 색칠 처리
        if(selected == i) {
            viewHolder.name.setBackgroundColor(Color.BLUE)
            viewHolder.id.setBackgroundColor(Color.BLUE)
            viewHolder.lab.setBackgroundColor(Color.BLUE)
            viewHolder.seat.setBackgroundColor(Color.BLUE)
            viewHolder.time.setBackgroundColor(Color.BLUE)
            viewHolder.status.setBackgroundColor(Color.BLUE)
        }
        else{
            viewHolder.name.setBackgroundResource(R.drawable.blue_corner)
            viewHolder.id.setBackgroundResource(R.drawable.blue_corner)
            viewHolder.lab.setBackgroundResource(R.drawable.blue_corner)
            viewHolder.seat.setBackgroundResource(R.drawable.blue_corner)
            viewHolder.time.setBackgroundResource(R.drawable.blue_corner)
            viewHolder.status.setBackgroundResource(R.drawable.blue_corner)
        }


        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        viewHolder.itemView.setOnClickListener {

            itemClickListener.onClick(it, i)
            selected = i
            viewHolder.name.setBackgroundColor(Color.BLUE)
            viewHolder.id.setBackgroundColor(Color.BLUE)
            viewHolder.lab.setBackgroundColor(Color.BLUE)
            viewHolder.seat.setBackgroundColor(Color.BLUE)
            viewHolder.time.setBackgroundColor(Color.BLUE)
            viewHolder.status.setBackgroundColor(Color.BLUE)

        }
    }

    override fun getItemCount(): Int {
        return todayreservationListArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.userName)
        val id: TextView = itemView.findViewById(R.id.userId)
        val lab: TextView = itemView.findViewById(R.id.labNum)
        val seat: TextView = itemView.findViewById(R.id.seatNum)
        val time: TextView = itemView.findViewById(R.id.usingTime)
        val status: TextView = itemView.findViewById(R.id.status)
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