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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

class ProductsByCategoryAdapter(
    private var productList: ArrayList<Product>,
    private val listener: ClickItemListener
) :
    RecyclerView.Adapter<ProductsByCategoryAdapter.ProductsByCategoryViewHolder>() {


    inner class ProductsByCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTextView: TextView = itemView.findViewById(R.id.product_name_grid)
        var imageView: ImageView = itemView.findViewById(R.id.product_image_grid)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsByCategoryViewHolder {

        return ProductsByCategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.product_item_grid,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsByCategoryViewHolder, position: Int) {

        val product = productList[position]
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(holder.itemView.context)
            .load(product.image_url)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .into(holder.imageView)
        holder.itemTextView.text = product.product_title

        holder.imageView.setOnClickListener {
            listener.onProductItemClicked(product)
        }
    }

    fun addData(products: List<Product>) {
        productList.addAll(products)
    }

    fun setData(products: ArrayList<Product>) {
        this.productList = products
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    interface ClickItemListener {
        fun onProductItemClicked(product: Product)
    }

}