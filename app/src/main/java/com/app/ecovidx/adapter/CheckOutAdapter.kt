package com.app.ecovidx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.ecovidx.R
import com.app.ecovidx.db.entities.Cart

class CheckOutAdapter(
    var items: List<Cart>
) : RecyclerView.Adapter<CheckOutAdapter.CheckOutViewHolder>() {

    inner class CheckOutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.co_item_name)
        var price: TextView = itemView.findViewById(R.id.co_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckOutViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.checkout_item, parent, false)
        return CheckOutViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckOutViewHolder, position: Int) {
        val cartItem = items[position]

        holder.itemName.text = cartItem.product
        holder.price.text = holder.itemView.context.getString(R.string.quantity_price, cartItem.quantity, cartItem.price)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}