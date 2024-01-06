package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowSelectTimeNewBinding
import com.callastro.model.CreateCalendarSchedule

class CalenderScheduleNewAppAdapter (val context : Context, var data: ArrayList<CreateCalendarSchedule>, private val listener: OnClick) :
    RecyclerView.Adapter<CalenderScheduleNewAppAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowSelectTimeNewBinding = DataBindingUtil.bind(itemView)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderScheduleNewAppAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_select_time_new, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CalenderScheduleNewAppAdapter.ViewHolder, position: Int) {
        val List = data[position]
        holder.binding.tvTimeFromNew.text = List.slottime1Show
        holder.binding.tvTimeToNew.text = List.slottime2Show
        holder.binding.tvDateViewNew.text = List.slotDate


        holder.binding.tvRemoveNew.setOnClickListener {

            row_index = position
            listener.onRemoveClicked(List)
            notifyDataSetChanged()
        }

      //  holder.binding.tvTimeDate.text = List.slotDate
      //  holder.binding.tvTime1.text = DateFormat.orderDateTime(List.slottime1)
     //   holder.binding.tvTime2.text = DateFormat.orderDateTime(List.slottime2)


    }

    override fun getItemCount(): Int {
        return data.size
    }


    interface OnClick{
        fun onRemoveClicked(createCalendarSchedule: CreateCalendarSchedule)
    }
}