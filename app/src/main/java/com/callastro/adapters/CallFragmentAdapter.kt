package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.RowHomeCallItemBinding
import com.callastro.model.CallUserListData

class CallFragmentAdapter (val context : Context, var list: List<CallUserListData>, private val listener: getId) :
    RecyclerView.Adapter<CallFragmentAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowHomeCallItemBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallFragmentAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_home_call_item, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: CallFragmentAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvName.text = data.name
        // holder.binding.tv.text = data.lastOnlineTime
        Glide.with(context).load(data.profile.toString()).into(holder.binding.ivPict)

        if(data.isLive!!.toString().equals("1")){
            // holder.binding.llButtons.visibility  = View.VISIBLE
            holder.binding.tvOnlineStatus.text = "Online"
            holder.binding.tvOnlineStatus.setTextColor(Color.parseColor("#145901"))
            //holder.binding.ivVideoCall.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_video_call1))
            // holder.binding.ivAudioCall.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_call1))
            holder.binding.ivVideoCall.setColorFilter(ContextCompat.getColor(context, R.color.theme_purple), android.graphics.PorterDuff.Mode.MULTIPLY);
            holder.binding.ivAudioCall.setColorFilter(ContextCompat.getColor(context, R.color.theme_purple), android.graphics.PorterDuff.Mode.MULTIPLY);


        } else if(data.isLive!!.toString().equals("0")){
            //   holder.binding.llButtons.visibility  = View.GONE
            holder.binding.tvOnlineStatus.text = "Online Time " + data.lastOnlineTime
            holder.binding.tvOnlineStatus.setTextColor(Color.parseColor("#B3B3B3"))
//            holder.binding.ivVideoCall.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_video_call1_gray))
//            holder.binding.ivAudioCall.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_call1_gray))
            holder.binding.ivVideoCall.setColorFilter(ContextCompat.getColor(context, R.color.theme_purple), android.graphics.PorterDuff.Mode.MULTIPLY);
            holder.binding.ivAudioCall.setColorFilter(ContextCompat.getColor(context, R.color.theme_purple), android.graphics.PorterDuff.Mode.MULTIPLY);

        }



        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.userId(data.userId.toString(),data.name.toString(),"1")
        })

        holder.binding.ivVideoCall.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.userId(data.userId.toString(),data.name.toString(),"2")
        })

        holder.binding.ivAudioCall.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.userId(data.userId.toString(),data.name.toString(),"3")
        })




    }

    override fun getItemCount(): Int {
        return list.size
    }
    //
//
//    interface OnClick{
//        fun onCallItemClicked(callUserListData: CallUserListData)
//        fun onVideoItemClicked(callUserListData: CallUserListData)
//        fun onAudioItemClicked(callUserListData: CallUserListData)
//    }
    interface getId{
        fun userId(id:String,username:String,type:String)
    }
}