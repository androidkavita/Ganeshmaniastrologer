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
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.RowChosenProductBinding
import com.callastro.model.SuggestRemedyListProducts

class SuggestRemedyChosenProductsAdapter (val context : Context, var data: ArrayList<SuggestRemedyListProducts>) :
    RecyclerView.Adapter<SuggestRemedyChosenProductsAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowChosenProductBinding = DataBindingUtil.bind(itemView)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestRemedyChosenProductsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_chosen_product, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SuggestRemedyChosenProductsAdapter.ViewHolder, position: Int) {
        val List = data[position]
        holder.binding.tvProductName.text = List.name
        Glide.with(context).load(List.mainImage).into(holder.binding.ivImage)

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