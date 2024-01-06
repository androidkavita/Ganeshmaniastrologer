package com.callastro.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowConfirmationbookingChatBinding
import com.callastro.model.MyBookingsUpcomingCompletedData

class MyBookingsUpcomingAdapter (val list: List<MyBookingsUpcomingCompletedData>, private val listener: OnClick
) : RecyclerView.Adapter<MyBookingsUpcomingAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowConfirmationbookingChatBinding= DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_confirmationbooking_chat, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvName.text = data.userName
        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onChatlItemClicked(data)
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface OnClick{
        fun onChatlItemClicked(myBookingsUpcomingCompletedData: MyBookingsUpcomingCompletedData)

    }
}