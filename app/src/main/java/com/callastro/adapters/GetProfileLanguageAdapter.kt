package com.callastro.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowExpertiseItemBinding
import com.callastro.model.GetAddedLanguageData

class GetProfileLanguageAdapter (val list: List<GetAddedLanguageData>, private val listener: OnClick
) : RecyclerView.Adapter<GetProfileLanguageAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowExpertiseItemBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_expertise_item, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        holder.binding.tvHead.text = data.languageName

        holder.binding.ivCross.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onLanguageItemDeleteClicked(data)
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
        fun onLanguageItemDeleteClicked(getAddedLanguageData: GetAddedLanguageData)
    }


}