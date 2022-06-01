package com.app.ecovidx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.ecovidx.R
import com.app.ecovidx.db.entities.Cart
import com.app.ecovidx.viewmodel.CartViewModel
import com.bumptech.glide.Glide

class CartAdapter(
    var items: List<Cart>,
    private val viewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    lateinit var item: Cart

    inner class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTextView: TextView = itemView.findViewById(R.id.cart_name)
        var quantity: TextView = itemView.findViewById(R.id.quantity)
        var price: TextView = itemView.findViewById(R.id.price)
        var itemImage: ImageView = itemView.findViewById(R.id.cart_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = items[position]
        item = cartItem
        holder.itemTextView.text = cartItem.product
        holder.quantity.text = cartItem.quantity.toString()
        holder.price.text = "£ " + cartItem.price.toString()
        Glide.with(holder.itemView.context)
            .load(cartItem.image)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}