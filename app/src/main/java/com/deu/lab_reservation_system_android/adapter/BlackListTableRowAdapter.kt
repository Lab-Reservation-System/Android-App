package com.deu.lab_reservation_system_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deu.lab_reservation_system_android.R
import com.deu.lab_reservation_system_android.model.row_format.blackList_show_format

class BlackListTableRowAdapter (private var blackListArrayList: ArrayList<blackList_show_format>) : RecyclerView.Adapter<BlackListTableRowAdapter.ViewHolder> () {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.table_row_layout_blacklist, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        viewHolder.id.text = blackListArrayList[i].id
        viewHolder.numberOfReport.text = blackListArrayList[i].numberOfReport.toString()
        viewHolder.restrictionEndDate.text = blackListArrayList[i].restrictionEndDate


    }

    override fun getItemCount(): Int {
        return blackListArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.id)
        val numberOfReport: TextView = itemView.findViewById(R.id.numberofReport)
        val restrictionEndDate: TextView = itemView.findViewById(R.id.restrictionEndDate)

    }
}