package com.callastro.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowCallHistoryBinding
import com.callastro.model.ChatHistoryListData

class ChatHistoryListAdapter (val list: List<ChatHistoryListData>, private val listener: OnClick
) : RecyclerView.Adapter<ChatHistoryListAdapter.ViewHolder>() {

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowCallHistoryBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_call_history, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
//        holder.binding.tvNationality.text = data.nationality
//        holder.binding.tvSessionType.text = data.session_type
        holder.binding.tvOrderId.text = "Order id: ${data.orderId.toString()}"
        holder.binding.tvUserName.text = data.userName
        holder.binding.tvDateTime.text = data.date + " ," + data.time
        holder.binding.tvDuration.text = data.duration
        holder.binding.tvCallRate.text = "Calling Charge: ₹ "+data.rate
        holder.binding.Earned.text = "Earned: ₹ "+data.amount
        holder.binding.tvFeedback.text = data.feedback
        holder.binding.ratingUser.setRating(data.rating!!.toFloat());
        holder.binding.tvStatus.text = data.status

        if (data.refund_status!!.equals(1)){
            holder.binding.llRefundAmount.visibility = View.GONE
        }else{
            holder.binding.llRefundAmount.visibility = View.VISIBLE
        }

        holder.binding.llSuggestRemedy.setOnClickListener(View.OnClickListener {
            listener.onSuggestRemedyClicked(data)
        })
        holder.binding.llRefundAmount.setOnClickListener(View.OnClickListener {
            listener.onRefundAmountClicked(data)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
        fun onSuggestRemedyClicked(chatHistoryListData: ChatHistoryListData)
        fun onRefundAmountClicked(chatHistoryListData: ChatHistoryListData)
    }

}