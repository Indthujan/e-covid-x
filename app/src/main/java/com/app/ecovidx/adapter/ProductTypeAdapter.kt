package com.app.ecovidx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.ecovidx.R
import com.app.ecovidx.data.model.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory


class ProductTypeAdapter(
    private val productList: List<Product>,
    private val listener: ClickItemListener
) :
    RecyclerView.Adapter<ProductTypeAdapter.ProductListViewHolder>() {

    inner class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTextView: TextView = itemView.findViewById(R.id.product_name)
        var imageView: ImageView = itemView.findViewById(R.id.product_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {

        return ProductListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.product_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {

        val product = productList[position]
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(holder.itemView.context)
            .load(product.image_url)
            .transition(withCrossFade(factory))
            .into(holder.imageView)
        holder.itemTextView.text = product.product_title

        holder.imageView.setOnClickListener {
            listener.onProductItemClicked(product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    interface ClickItemListener {
        fun onProductItemClicked(product: Product)
    }

}