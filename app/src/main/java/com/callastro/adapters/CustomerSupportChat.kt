package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.callastro.R
import com.callastro.databinding.RowChatwithusBinding
import com.callastro.model.ChatListMessageData
import com.callastro.model.GetCustomerSupportChatData
import com.maxtra.callastro.prefs.UserPref
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.Date

class CustomerSupportChat(
    private val context: Context,
    messageList: List<GetCustomerSupportChatData>
) : RecyclerView.Adapter<CustomerSupportChat.MyViewHolder>() {
     private val messageList: List<GetCustomerSupportChatData>
        private val userPref: UserPref


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyViewHolder {
            val itemView: View =
                LayoutInflater.from(parent.context).inflate(R.layout.row_chatwithus, parent, false)
            return MyViewHolder(itemView)
        }


        @SuppressLint("SimpleDateFormat")
        fun getTimeStampDate(str_date: String): String? {
            var str_date = str_date
            val format = SimpleDateFormat("dd-MMM-yy  hh.mm a")
            if (str_date.length < 13) {
                str_date = (java.lang.Long.valueOf(str_date) * 1000).toString() + ""
            }
            var date: Date? = null
            try {
                date = Date(Long.valueOf(str_date))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            assert(date != null)
            var timestampValue: String? = ""
            try {
                timestampValue = format.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return timestampValue
        }

        override fun onBindViewHolder(
            holder: MyViewHolder,
            position: Int
        ) {


            if (messageList[position].type.toString().equals("1")
            ) {
                holder.binding!!.cvMy.visibility = View.VISIBLE
                holder.binding.cvOther.visibility = View.GONE

                if (messageList[position].createdAt != null && !messageList[position].createdAt
                    !!.equals("")
                ) {
                    holder.binding.tvDateTimeMy.text = /*getTimeStampDate(*/
                        messageList[position].createdAt!!.toString()
//                )
                } else {
                    holder.binding.tvDateTimeMy.text = ""
                }
//
//            holder.binding.tvMsgMy.text = messageList[position]
//                .message
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.binding.tvMsgMy.setText(Html.fromHtml(messageList[position].message, Html.FROM_HTML_MODE_LEGACY));
                } else
                    holder.binding.tvMsgMy.setText(Html.fromHtml(messageList[position].message));

                holder.binding.tvMsgMy.visibility = View.VISIBLE


            } else {
                holder.binding!!.cvMy.visibility = View.GONE
                holder.binding.cvOther.visibility = View.VISIBLE

                if (messageList[position].createdAt != null && !messageList[position].createdAt
                    !!.equals("")
                ) {
                    holder.binding.tvDateTimeOther.text =
                            /* getTimeStampDate(*/messageList[position].createdAt!!.toString()/*)*/

                } else {
                    holder.binding.tvDateTimeOther.text = ""
                }

//            holder.binding.tvMsgOther.text =
//                if (messageList[position].message != null) messageList[position]
//                    .message else ""
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.binding.tvMsgOther.setText(Html.fromHtml(messageList[position].message, Html.FROM_HTML_MODE_LEGACY));
                } else
                    holder.binding.tvMsgOther.setText(Html.fromHtml(messageList[position].message));
                holder.binding.tvMsgOther.visibility = View.VISIBLE

            }
        }

        override fun getItemCount(): Int {
            return messageList.size
        }

        inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
            val binding: RowChatwithusBinding? = DataBindingUtil.bind(view!!)

        }

        init {
            this.messageList = messageList
            userPref = UserPref(this.context)
        }


}