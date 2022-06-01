package com.app.ecovidx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.ecovidx.R
import com.app.ecovidx.data.model.Slider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

class SliderAdapter(private val sliderList: List<Slider>) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.slider_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slider_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val slider = sliderList[position]
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(holder.itemView.context)
            .load(slider.url)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return sliderList.size
    }
}