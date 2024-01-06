package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.model.CityListData
import com.maxtra.astrorahiastrologer.clicklistener.PopupItemClickListenerCountry


class CityListAdapter (

    var context: Context,
    var data: ArrayList<CityListData>,
    var flag: String,
    var click: PopupItemClickListenerCountry

) :
    RecyclerView.Adapter<CityListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.row_spinner_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var ListData = data.get(position)


        if (flag == "City") {
            holder.Names.text = ListData.cityName
            holder.Names.setOnClickListener {
                ListData.cityName.let { it1 ->
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
    fun filterList(filteredCityList: ArrayList<CityListData>) {
        data = filteredCityList
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
class CityListAdapter (val context : Context, var list: List<CityListData>, private val listener: OnClick) :
    RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowSpinnerItemBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_spinner_item, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CityListAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvName.text = data.cityName.toString()

        holder.binding.linearItemSpinner.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onItemClicked(data)
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClick{
        fun onItemClicked(cityListData: CityListData)

    }
}*/
