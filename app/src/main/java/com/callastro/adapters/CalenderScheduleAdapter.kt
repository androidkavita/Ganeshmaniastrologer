package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowSelectTimeBinding
import com.callastro.model.CalenderListData

class CalenderScheduleAdapter (val context : Context, var data: ArrayList<CalenderListData>, private val listener: OnClick) :
    RecyclerView.Adapter<CalenderScheduleAdapter.ViewHolder>() {

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowSelectTimeBinding = DataBindingUtil.bind(itemView)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderScheduleAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_select_time, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CalenderScheduleAdapter.ViewHolder, position: Int) {
        val List = data[position]
       holder.binding.tvTimeFrom.text = List.fromTime.toString()
        holder.binding.tvTimeTo.text = List.toTime.toString()
        holder.binding.tvDateView.text = List.date.toString()


//        Log.d(TAG, "onBindViewHolde6r:AG, "onBindViewHolde6r: "+DateFormat.dealstimeforedit(List.fromTime.toString()))
//        Log.d(TAG, "onBindViewHolde6r: "+DateFormat.getDealTime(List.fromTime.toString()))
//        Log.d(TAG, "onBindViewHolde6r: "+DateFormat.dealstime(List.fromTime.toString()))
//        Log.d(TAG, "onBindViewHolde6r: "+DateFormat.dealstimeforedit(List.fromTime.toString())) "+DateFormat.getDealTime(List.fromTime.toString()))
//        Log.d(TAG, "onBindViewHolde6r: "+DateFormat.dealstime(List.fromTime.toString()))
//        Log.d(TAG, "onBindViewHolde6r: "+DateFormat.dealstimeforedit(List.fromTime.toString()))



        holder.binding.tvRemove.setOnClickListener(View.OnClickListener {
            listener.onRemovedFromApiClicked(List)
        })
    }

    override fun getItemCount(): Int {
        return data.size
    }
    interface OnClick{
        fun onRemovedFromApiClicked(calenderListData: CalenderListData)
    }
}