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
import com.callastro.databinding.RowfixedsessionBinding
import com.callastro.model.FixedsessionResponseListData

class FixedSessionAdapter (val context : Context, var List: ArrayList<FixedsessionResponseListData>, private val listener: OnClick) :
    RecyclerView.Adapter<FixedSessionAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowfixedsessionBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixedSessionAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rowfixedsession, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FixedSessionAdapter.ViewHolder, position: Int) {
        val data  = List[position]

        holder.binding.tvName.text = data.userName.toString()
        holder.binding.tvLanguage.text = data.language.toString()

        if (data.fix_session_type!!.equals(1)){
            holder.binding.btntime.text = "Fixed session for 30 min"
        }else{
            holder.binding.btntime.text = "Fixed session for 60 min"
        }


        if (data.type!!.equals(1)) {
            holder.binding.typeCall.text = "Chat"
            holder.binding.callchaticon.setImageResource(R.drawable.ic_chat)
        }else if (data.type!!.equals(2)){
            holder.binding.typeCall.text = "Audio Call"
            holder.binding.callchaticon.setImageResource(R.drawable.ic_call1)
        }else{
            holder.binding.typeCall.text = "Video Call"
            holder.binding.callchaticon.setImageResource(R.drawable.ic_call1)
        }


        Glide.with(context).load(data.profile.toString()).into(holder.binding.image)


        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onCallItemClicked(data)
        })

        holder.binding.llAccept.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onCallAcceptClicked(data)
            holder.binding.llCallHandler.visibility = View.GONE
        })
        holder.binding.llCancel.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onCallCancelClicked(data)
            holder.binding.llCallHandler.visibility = View.GONE
        })


//        holder.binding.llCall.visibility = View.GONE
//        holder.binding.llAudioCall.visibility = View.VISIBLE


     /*   holder.binding.llStartChat.setOnClickListener(View.OnClickListener {
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






        if(data.requestStatus!!.toString().equals("1"))
        {
            holder.binding.llCallHandler.visibility = View.VISIBLE
        }
        else if(data.requestStatus!!.toString().equals("7"))
        {
            holder.binding.llCallHandler.visibility = View.GONE
        }
        else if(data.requestStatus!!.toString().equals("3"))
        {
            holder.binding.llCallHandler.visibility = View.GONE
        }



    }

    override fun getItemCount(): Int {
        return List.size
    }
    interface OnClick{
        fun onCallItemClicked(fixedsessionResponseListData: FixedsessionResponseListData)
        fun onCallAcceptClicked(fixedsessionResponseListData: FixedsessionResponseListData)
        fun onCallCancelClicked(fixedsessionResponseListData: FixedsessionResponseListData)
        fun onStartCallButtonClicked(fixedsessionResponseListData: FixedsessionResponseListData)
    }
}

