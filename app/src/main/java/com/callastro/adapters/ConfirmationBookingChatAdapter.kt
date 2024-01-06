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
import com.callastro.databinding.RowConfirmationbookingChatBinding
import com.callastro.model.ConfirmationBookingData

class ConfirmationBookingChatAdapter (val context : Context, var list: List<ConfirmationBookingData>, private val listener: OnClick) :
    RecyclerView.Adapter<ConfirmationBookingChatAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowConfirmationbookingChatBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfirmationBookingChatAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_confirmationbooking_chat, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ConfirmationBookingChatAdapter.ViewHolder, position: Int) {
        val data = list[position]
       holder.binding.tvName.text = data.userName
        holder.binding.tvLanguage.text = data.language
        holder.binding.tvPhn.text = data.mobileNo
       Glide.with(context).load(data.profile.toString()).into(holder.binding.ivUserPic)




        holder.binding.llStartChat.setOnClickListener(View.OnClickListener {
                row_index = position
                listener.onChatItemClicked(data)
        })

        holder.binding.ivNext.setOnClickListener(View.OnClickListener {
            //    row_index = position
            //    listener.onCallItemClicked(data)
        })

       /* holder.binding.llChat.setOnClickListener(View.OnClickListener {
                //row_index = position
               // listener.onChatItemClicked(data)
        })*/

        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onListItemClicked(data)
        })


    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
        fun onChatItemClicked(confirmationBookingData: ConfirmationBookingData)
        fun onListItemClicked(confirmationBookingData: ConfirmationBookingData)

    }
}