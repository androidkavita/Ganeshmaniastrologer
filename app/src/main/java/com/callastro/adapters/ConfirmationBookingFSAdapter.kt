package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.RowConfirmationbookingFixedsessionBinding
import com.callastro.model.ConfirmationBookingData

class ConfirmationBookingFSAdapter(
    val context: Context,
    var list: List<ConfirmationBookingData>,
    private val listener: OnClick
) :
    RecyclerView.Adapter<ConfirmationBookingFSAdapter.ViewHolder>() {
    var row_index: Int = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowConfirmationbookingFixedsessionBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConfirmationBookingFSAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_confirmationbooking_fixedsession, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ConfirmationBookingFSAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvName.text = data.userName
        holder.binding.tvLanguage.text = data.language
        holder.binding.tvPhn.text = data.mobileNo
        holder.binding.tvFixedSessionTime.text = "Fixed Session: " + data.fix_session
        Glide.with(context).load(data.profile.toString()).into(holder.binding.ivUserPic)

        if (data.active_status == 1) {
            holder.binding.start.text = "Active"
        } else {
            holder.binding.start.text = "Waiting"
        }


        holder.binding.llStartCall.setOnClickListener(View.OnClickListener {
            if (data.active_status == 1) {
                row_index = position
                listener.onListItemClicked(data)
            } else {
                Toast.makeText(context, "session not start", Toast.LENGTH_SHORT).show()
              }
          }
        )

        holder.binding.ivNext.setOnClickListener(View.OnClickListener {
            //    row_index = position
            //    listener.onCallItemClicked(data)
        })

        holder.binding.llAudioCall.setOnClickListener(View.OnClickListener {
            //    row_index = position
            //    listener.onCallItemClicked(data)
        })








        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onListItemClicked(data)
        })


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


    interface OnClick {
        fun onListItemClicked(confirmationBookingData: ConfirmationBookingData)

    }
}