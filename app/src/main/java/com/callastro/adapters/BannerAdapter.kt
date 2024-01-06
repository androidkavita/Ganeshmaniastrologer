package com.callastro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.callastro.R
import com.callastro.model.BannerResponseData
import com.smarteist.autoimageslider.SliderViewAdapter

class BannerAdapter ( private val sliderDataArrayList: ArrayList<BannerResponseData>) :
    SliderViewAdapter<BannerAdapter.BannerAdapterViewHolder>() {

    class BannerAdapterViewHolder(itemView: View) :
        ViewHolder(itemView) {

        var imageViewBackground: ImageView

        init {
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): BannerAdapter.BannerAdapterViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, null)
        return BannerAdapterViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: BannerAdapter.BannerAdapterViewHolder, position: Int) {
        val sliderItem = sliderDataArrayList[position]

        Glide.with(viewHolder.itemView)
            .load(sliderItem.image)
            .fitCenter()
            .into(viewHolder.imageViewBackground)
    }

    override fun getCount(): Int {
        return sliderDataArrayList.size
    }
}