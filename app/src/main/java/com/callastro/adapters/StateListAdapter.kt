package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.callastro.R
import com.callastro.model.StateListData
import com.maxtra.astrorahiastrologer.clicklistener.PopupItemClickListenerCountry

class StateListAdapter (

    var context: Context,
    var data: ArrayList<StateListData>,
    var flag: String,
    var click: PopupItemClickListenerCountry

) :
    RecyclerView.Adapter<StateListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.row_spinner_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var ListData = data.get(position)


        if (flag == "State") {
            holder.Names.text = ListData.stateName
            holder.Names.setOnClickListener {
                ListData.stateName.let { it1 ->
                    if (it1 != null) {
                        ListData.id?.let { it2 -> click.getCountry(it1, flag, it2.toInt()) }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredStateList: ArrayList<StateListData>) {
        data = filteredStateList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Names: TextView


        init {
            Names = itemView.findViewById(R.id.tvName)
        }
    }

}


/*
class StateListAdapter (val context : Context, var list: List<StateListData>, private val listener: OnClick) :
    RecyclerView.Adapter<StateListAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowSpinnerItemBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_spinner_item, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StateListAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvName.text = data.stateName.toString()

        holder.binding.linearItemSpinner.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onItemClicked(data)
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClick{
        fun onItemClicked(stateListData: StateListData)

    }
}*/
