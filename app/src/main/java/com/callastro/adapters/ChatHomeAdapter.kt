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
import com.callastro.databinding.RowChatRequestBinding
import com.callastro.model.Chat_Call_ResponseData

class ChatHomeAdapter (val context : Context, var list: List<Chat_Call_ResponseData>, private val listener:OnClick) :
    RecyclerView.Adapter<ChatHomeAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowChatRequestBinding = DataBindingUtil.bind(itemView)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHomeAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_chat_request, parent, false)
        return ViewHolder(itemView)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChatHomeAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvName.text = data.userName.toString()
        holder.binding.tvLanguage.text = data.language.toString()
      //  Glide.with(context).load(data.toString()).into(holder.binding.image)
        Glide.with(context).load(data.profile.toString()).into(holder.binding.image)


        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onChatItemClicked(data)
        })

        holder.binding.llAccept.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onChatAcceptClicked(data)
        })
        holder.binding.llCancel.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onChatCancelClicked(data)
        })


        holder.binding.llStartChat.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onStartChatButtonClicked(data)
        })






        if(data.requestStatus!!.toString().equals("1"))
        {
            holder.binding.llAccept.visibility = View.VISIBLE
            holder.binding.llCancel.visibility = View.VISIBLE
            holder.binding.llStartChat.visibility = View.GONE

        }
       else if(data.requestStatus!!.toString().equals("2"))
        {
            holder.binding.llAccept.visibility = View.GONE
            holder.binding.llCancel.visibility = View.GONE
            holder.binding.llStartChat.visibility = View.VISIBLE

        }

        else if(data.requestStatus!!.toString().equals("3"))
        {
            holder.binding.llAccept.visibility = View.GONE
            holder.binding.llCancel.visibility = View.GONE
            holder.binding.llStartChat.visibility = View.GONE
        }





    }
    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClick{
        fun onChatItemClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
        fun onChatAcceptClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
        fun onChatCancelClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
        fun onStartChatButtonClicked(chat_Call_ResponseData: Chat_Call_ResponseData)


    }
}