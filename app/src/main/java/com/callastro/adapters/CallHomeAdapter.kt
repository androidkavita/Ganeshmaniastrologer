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
import com.callastro.databinding.RowCallRequestBinding
import com.callastro.model.Chat_Call_ResponseData

class CallHomeAdapter (val context : Context, var list: List<Chat_Call_ResponseData>, private val listener: OnClick) :
    RecyclerView.Adapter<CallHomeAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowCallRequestBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallHomeAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_call_request, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CallHomeAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvName.text = data.userName.toString()
        holder.binding.tvLanguage.text = data.language.toString()
        Glide.with(context).load(data.profile.toString()).into(holder.binding.image)
        if (data.type == 2){
            holder.binding.calltype.text = "Audio Call"
        }else{
            holder.binding.calltype.text = "Video Call"
        }


        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onCallItemClicked(data)
        })

        holder.binding.llAccept.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onCallAcceptClicked(data)
        })

        holder.binding.llCancel.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onCallCancelClicked(data)
        })


        holder.binding.llCall.visibility = View.GONE
        holder.binding.llAudioCall.visibility = View.VISIBLE


        holder.binding.llStartCall.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onStartCallButtonClicked(data)
        })

//        if(data.mobile.equals("")||data.mobile.equals(null)){
//            holder.binding.llAudioCall.visibility = View.GONE
//        }
//        else{
//            holder.binding.llAudioCall.visibility = View.VISIBLE
//
//        }






        if(data.requestStatus!!.toString().equals("1"))
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

    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
        fun onCallItemClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
        fun onCallAcceptClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
        fun onCallCancelClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
        fun onStartCallButtonClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
    }
}