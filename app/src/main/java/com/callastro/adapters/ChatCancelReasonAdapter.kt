package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowCancelReasonsBinding
import com.callastro.model.ChatCallCancelReasonData

import java.util.ArrayList

class ChatCancelReasonAdapter (val context: Context,
                               val list: List<ChatCallCancelReasonData>,
  //  private val listener: OnClick
) : RecyclerView.Adapter<ChatCancelReasonAdapter.ViewHolder>() {

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowCancelReasonsBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_cancel_reasons, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        holder.binding.cbReason.text = data.reason


        /*holder.binding.llViewdetails.setOnClickListener(View.OnClickListener {
          val intent = Intent(context, TripDetailsActivity::class.java)
          intent.putExtra("orderType", "4")
            context.startActivity(intent)
        })*/


         var listreasonType = ArrayList<String>()
         var listreasonType_id = ArrayList<String>()


        /*holder.binding.cbReason.setOnClickListener(View.OnClickListener { view ->
            if ((view as CompoundButton).isChecked) {

                val reasonvalue  = data.reason
                val reasonvalue_id = data.id.toString()

                listreasonType.add(data.reason!!)
                listreasonType_id.add(data.id.toString())
                TripDetailsActivity().checkBox(data.id)
               // Toast.makeText(context, "Selected CheckBox ID" + data.id, Toast.LENGTH_SHORT).show();
            } else {
                listreasonType.remove(data.reason)
                listreasonType_id.remove(data.id.toString())
                println("Un-Checked")
            }
        })*/


    }

    override fun getItemCount(): Int {
        return list.size
    }



}