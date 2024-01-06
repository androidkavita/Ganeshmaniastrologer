package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowFaqBinding
import com.callastro.model.FAQResponseData

class FAQAdapter (val context : Context, var data: ArrayList<FAQResponseData>) :
    RecyclerView.Adapter<FAQAdapter.ViewHolder>() {

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowFaqBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_faq, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FAQAdapter.ViewHolder, position: Int) {
        val List = data[position]
        holder.binding.tvHead.text = List.questions.toString()
        holder.binding.tvSubhead.text = List.answers.toString()


        holder.binding.arrowright.setOnClickListener {
            holder.binding.arrowdown.visibility = View.VISIBLE
            holder.binding.arrowright.visibility = View.GONE
            holder.binding.tvSubhead.visibility =View.VISIBLE
        }

        holder.binding.arrowdown.setOnClickListener {
            holder.binding.arrowdown.visibility = View.GONE
            holder.binding.arrowright.visibility = View.VISIBLE
            holder.binding.tvSubhead.visibility =View.GONE
        }



    }

    override fun getItemCount(): Int {
        return data.size
    }

}