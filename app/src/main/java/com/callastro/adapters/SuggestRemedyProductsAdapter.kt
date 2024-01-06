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
import com.callastro.databinding.RowProductBinding
import com.callastro.model.ProductListData

class SuggestRemedyProductsAdapter (val context : Context, var list: List<ProductListData>, private val listener: OnClick) :
    RecyclerView.Adapter<SuggestRemedyProductsAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowProductBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestRemedyProductsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_product, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SuggestRemedyProductsAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvProductName.text = data.name
        Glide.with(context).load(data.mainImage.toString()).into(holder.binding.ivProduct)

        /*holder.binding.cbTick.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onProductItemClicked(data)
        })*/

        holder.binding.cbTick.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // show toast , check box is checked
                row_index = position
                listener.onProductItemAddClicked(data)
            } else {
                // show toast , check box is not checked
                row_index = position
                listener.onProductItemRemoveClicked(data)
            }
        }


        /*if(data.requestStatus!!.toString().equals("1"))
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

*/











    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
        fun onProductItemAddClicked(productListData: ProductListData)
        fun onProductItemRemoveClicked(productListData: ProductListData)

    }
}