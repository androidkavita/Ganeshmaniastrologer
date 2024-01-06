package com.callastro.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.databinding.RowSelectRatingReviewBinding
import com.callastro.model.GetAstroRatingReviewReviewRatings


class ReviewRatingSelectAdapter (val context : Context, var list: List<GetAstroRatingReviewReviewRatings>, private val listener: OnClick) :
    RecyclerView.Adapter<ReviewRatingSelectAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowSelectRatingReviewBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewRatingSelectAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_select_rating_review, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReviewRatingSelectAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvUserName.text = data.userName.toString()+data.id
        holder.binding.tvDetail.text = data.reveiw.toString()
        Glide.with(context).load(data.profile.toString()).into(holder.binding.ivImage)
        holder.binding.ratingByUser.setRating(data.rating!!.toFloat());

        if(data.pin!! == 1){
            holder.binding.cbReview.isChecked = true
            //notifyDataSetChanged()
            Log.d("CheckBoxInfo_adap","str_got1__"+ data.id!! )
        }
       else if(data.pin!! == 0){
            holder.binding.cbReview.isChecked = false
           // notifyDataSetChanged()
            Log.d("CheckBoxInfo","str_got0__"+ data.id!! )
        }

        holder.binding.cbReview.setOnClickListener(View.OnClickListener {

            row_index = position
            var checkAdd: String?   =""

            if(holder.binding.cbReview.isChecked){
                Toast.makeText(context, "checked", Toast.LENGTH_SHORT).show()
                checkAdd = "1"

            }
            else if(!holder.binding.cbReview.isChecked) {
                Toast.makeText(context, "unchecked", Toast.LENGTH_SHORT).show()
                checkAdd = "0"
            }
            listener.onRatingItemClicked(data,checkAdd.toString())

        })


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface OnClick{
        fun onRatingItemClicked(getAstroRatingReviewReviewRatings: GetAstroRatingReviewReviewRatings, checkAdd :String)

    }
}