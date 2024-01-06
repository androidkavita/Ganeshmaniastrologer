package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowSpinnerItemBinding
import com.callastro.model.SuggestRemedyListSuggestedMsgt

class SuggestRemedyTextAdapter (val context : Context, var data: ArrayList<SuggestRemedyListSuggestedMsgt>) :
    RecyclerView.Adapter<SuggestRemedyTextAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowSpinnerItemBinding = DataBindingUtil.bind(itemView)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestRemedyTextAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_spinner_item, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SuggestRemedyTextAdapter.ViewHolder, position: Int) {
        val List = data[position]
        holder.binding.tvName.text = List.suggest.toString()

        /*holder.binding.tvRemoveNew.setOnClickListener {
            row_index = position
            listener.onRemoveClicked(List)
            notifyDataSetChanged()
        }*/

    }

    override fun getItemCount(): Int {
        return data.size
    }



}