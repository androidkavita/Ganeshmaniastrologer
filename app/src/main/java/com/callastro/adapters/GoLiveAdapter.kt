package com.callastro.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.LivecommentBinding
import com.callastro.model.LiveCommentsModelClassData
import com.maxtra.callastro.prefs.UserPref

class GoLiveAdapter (
    var context: Context,  var arrayList:ArrayList<LiveCommentsModelClassData>
) :
    RecyclerView.Adapter<GoLiveAdapter.Holder>() {
    lateinit var thelastmessage:String
    lateinit var userPref: UserPref
    var lastmessage=""
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: LivecommentBinding = DataBindingUtil.bind(itemView)!!
//        var card_item_click = itemView.card_item_click
//        var tv_name_person = itemView.tv_name_person
//        var person_image = itemView.person_image
//        var message = itemView.message

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.livecomment, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val list=arrayList[position]
        holder.binding.tvNamePerson.text=list.name
        Glide.with(context).load(list.profile).into(holder.binding.personImage)
        if (list.type!!.equals(1)){
            holder.binding.message.visibility = View.VISIBLE
            holder.binding.giftImage.visibility = View.GONE
            holder.binding.message.text=list.message
        }else if (list.type!!.equals(2)){
            holder.binding.giftImage.visibility = View.VISIBLE
            holder.binding.message.visibility = View.GONE
            Glide.with(context).load(list.image).into(holder.binding.giftImage)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    interface onItemClick {
        fun OnItemClick(position: Int, title: String,id:String,image:String)
    }


}