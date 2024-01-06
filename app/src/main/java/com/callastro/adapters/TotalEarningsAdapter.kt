package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowEarningBinding
import com.callastro.model.AstroEarningListResponseData
import com.callastro.model.AstroEarningTransactionHistory

class TotalEarningsAdapter (val context : Context, var list: List<AstroEarningListResponseData>) :
    RecyclerView.Adapter<TotalEarningsAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowEarningBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalEarningsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_earning, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TotalEarningsAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvPaymentType.text = data.content
        holder.binding.tvAmount.text = "₹"+data.amount
//        holder.binding.tvPaymentFrom.text = data.transaction
        holder.binding.tvPaymentDate.text = data.createdAt+" " + data.createdTime


    }

    override fun getItemCount(): Int {
        return list.size
    }
}