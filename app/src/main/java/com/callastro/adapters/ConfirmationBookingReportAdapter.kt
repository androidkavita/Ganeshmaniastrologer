package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.RowConfirmationbookingReportBinding
import com.callastro.model.ConfirmationBookingData

class ConfirmationBookingReportAdapter (val context : Context, var list: List<ConfirmationBookingData>, private val listener: OnClick) :
    RecyclerView.Adapter<ConfirmationBookingReportAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowConfirmationbookingReportBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfirmationBookingReportAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_confirmationbooking_report, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ConfirmationBookingReportAdapter.ViewHolder, position: Int) {
        val data = list[position]
       holder.binding.tvName.text = data.userName
        holder.binding.tvLanguage.text = data.language
        holder.binding.tvPhn.text = data.mobileNo
       Glide.with(context).load(data.profile.toString()).into(holder.binding.ivUserPic)




        holder.binding.llReport.setOnClickListener(View.OnClickListener {
            //    row_index = position
            //    listener.onCallItemClicked(data)
        })

        holder.binding.ivNext.setOnClickListener(View.OnClickListener {
            //    row_index = position
            //    listener.onCallItemClicked(data)
        })








        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onListItemClicked(data)
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
        fun onListItemClicked(confirmationBookingData: ConfirmationBookingData)

    }
}