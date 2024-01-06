package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowNotificationItemBinding
import com.callastro.model.NotificationData
import com.callastro.model.NotificationListData

class NotificationAdapter (val context : Context, var data: ArrayList<NotificationData>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    var row_index: Int = -1
    var isread:Int = 0
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowNotificationItemBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_notification_item, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        val List = data[position]
        holder.binding.tvHead.text = List.title.toString()
        holder.binding.tvDetail.text = List.message.toString()
        isread = List.isRead!!.toInt()
        holder.binding.tvDate.text = List.time
        if (isread.equals(1)){
            holder.binding.tvCount.visibility = View.VISIBLE
        }else{
            holder.binding.tvCount.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

//    interface OnClick{
//        fun onNotificationItemClicked(notificationListData: NotificationListData)
//    }
}