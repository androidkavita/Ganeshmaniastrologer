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
import com.callastro.databinding.RowPostprofileReviewsBinding
import com.callastro.model.GetAstroRatingReviewReviewRatings

class ReviewRatingAllAdapter (val context : Context, var list: List<GetAstroRatingReviewReviewRatings>, private val listener: OnClick) :
    RecyclerView.Adapter<ReviewRatingAllAdapter.ViewHolder>() {
    var row_index: Int = -1
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowPostprofileReviewsBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewRatingAllAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_postprofile_reviews, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReviewRatingAllAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvUserName.text = data.userName.toString()
        holder.binding.tvDetail.text = data.reveiw.toString()
        Glide.with(context).load(data.profile.toString()).into(holder.binding.ivImage)
        holder.binding.ratingByUser.setRating(data.rating!!.toFloat());


        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            row_index = position
            listener.onRatingItemClicked(data)
        })



    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnClick{
        fun onRatingItemClicked(getAstroRatingReviewReviewRatings: GetAstroRatingReviewReviewRatings)
    }
}