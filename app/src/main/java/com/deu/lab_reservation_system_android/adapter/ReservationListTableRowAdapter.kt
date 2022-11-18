package com.deu.lab_reservation_system_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.row_format.reservation_show_format

class ReservationListTableRowAdapter (private var reservationListArrayList: ArrayList<reservation_show_format>) : RecyclerView.Adapter<ReservationListTableRowAdapter.ViewHolder> () {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.table_row_layout_reservation_list, viewGroup, false) //
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        viewHolder.date.text = reservationListArrayList[i].date
        viewHolder.name.text = reservationListArrayList[i].name
        viewHolder.id.text = reservationListArrayList[i].id
        viewHolder.lab.text = reservationListArrayList[i].labNumber
        viewHolder.seat.text = reservationListArrayList[i].seat
        viewHolder.time.text = reservationListArrayList[i].time
        viewHolder.status.text = reservationListArrayList[i].permissionState.toString()

    }

    override fun getItemCount(): Int {
        return reservationListArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.usingDate)
        val name: TextView = itemView.findViewById(R.id.userName)
        val id: TextView = itemView.findViewById(R.id.userId)
        val lab: TextView = itemView.findViewById(R.id.labNum)
        val seat: TextView = itemView.findViewById(R.id.seatNum)
        val time: TextView = itemView.findViewById(R.id.usingTime)
        val status: TextView = itemView.findViewById(R.id.status)

    }
}