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
import com.callastro.model.AstroEarningTransactionHistory

class EarningsAdapter (val context : Context, var list: List<AstroEarningTransactionHistory>, private val listener: OnClick) :
    RecyclerView.Adapter<EarningsAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowEarningBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_earning, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EarningsAdapter.ViewHolder, position: Int) {
        val data = list[position]
       holder.binding.tvPaymentType.text = data.userName
        holder.binding.tvAmount.text = "₹"+data.amount
        holder.binding.tvPaymentFrom.text = data.transaction
        holder.binding.tvPaymentDate.text = data.payDate
        //Glide.with(context).load(data.profile.toString()).into(holder.binding.image)


        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onCallItemClicked(data)
        })










        /*if(data.requestStatus!!.toString().equals("1"))
        {
            holder.binding.llAccept.visibility = View.VISIBLE
            holder.binding.llCancel.visibility = View.VISIBLE
            holder.binding.llStartCall.visibility = View.GONE

        }
        else if(data.requestStatus!!.toString().equals("2"))
        {
            holder.binding.llAccept.visibility = View.GONE
            holder.binding.llCancel.visibility = View.GONE
            holder.binding.llStartCall.visibility = View.VISIBLE

        }

        else if(data.requestStatus!!.toString().equals("3"))
        {
            holder.binding.llAccept.visibility = View.GONE
            holder.binding.llCancel.visibility = View.GONE
            holder.binding.llStartCall.visibility = View.GONE
        }

*/











    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
        fun onCallItemClicked(astroEarningTransactionHistory: AstroEarningTransactionHistory)

    }
}