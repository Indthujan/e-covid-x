package com.app.ecovidx.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ecovidx.R
import com.app.ecovidx.data.model.OrderList
import com.app.ecovidx.databinding.OrderItemBinding

class OrderHistoryAdapter(
    var item: List<OrderList>
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    inner class OrderHistoryViewHolder(var viewBinding: OrderItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val view =
            OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val order = item[position]
        holder.viewBinding.dateTxt.text =
            holder.itemView.context.getString(R.string.date_text, order.post_date)
        holder.viewBinding.statusTxt.text =
            holder.itemView.context.getString(R.string.status_text, order.post_status)
    }

    override fun getItemCount(): Int {
        return item.size
    }
}