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
import com.callastro.databinding.RowCancellationByUserBinding
import com.callastro.model.CancellationByUserData

class CancellationByUserAdapter (val context : Context, var list: List<CancellationByUserData>) :
    RecyclerView.Adapter<CancellationByUserAdapter.ViewHolder>() {
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowCancellationByUserBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_cancellation_by_user, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvName.text = data.toString()
        holder.binding.tvLanguage.text = data.language.toString()
        Glide.with(context).load(data.profile.toString()).into(holder.binding.ivUser)


        /*holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onCallItemClicked(data)
        })*/

       /* holder.binding.llAccept.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onCallAcceptClicked(data)
        })
        holder.binding.llCancel.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onCallCancelClicked(data)
        })*/


      /*  holder.binding.llCall.visibility = View.GONE
        holder.binding.llAudioCall.visibility = View.VISIBLE


        holder.binding.llStartCall.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onStartCallButtonClicked(data)
        })*/

//        if(data.mobile.equals("")||data.mobile.equals(null)){
//            holder.binding.llAudioCall.visibility = View.GONE
//        }
//        else{
//            holder.binding.llAudioCall.visibility = View.VISIBLE
//
//        }




//1-Chat Requests,2-Call Requests, 3-Video Call Requests

       if(data.type!!.toString().equals("1"))
        {
            holder.binding.llChat.visibility = View.VISIBLE
            holder.binding.llAudioCall.visibility = View.GONE
            holder.binding.llVideoCall.visibility = View.GONE

        }
        else if(data.type!!.toString().equals("2"))
        {
            holder.binding.llChat.visibility = View.GONE
            holder.binding.llAudioCall.visibility = View.VISIBLE
            holder.binding.llVideoCall.visibility = View.GONE

        }

        else if(data.type!!.toString().equals("3"))
        {
            holder.binding.llChat.visibility = View.GONE
            holder.binding.llAudioCall.visibility = View.GONE
            holder.binding.llVideoCall.visibility = View.VISIBLE
        }













    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
       // fun onCallItemClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
      //  fun onCallAcceptClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
      //  fun onCallCancelClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
      //  fun onStartCallButtonClicked(chat_Call_ResponseData: Chat_Call_ResponseData)
    }
}