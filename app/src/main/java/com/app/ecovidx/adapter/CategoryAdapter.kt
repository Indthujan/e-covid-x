package com.app.ecovidx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.ecovidx.R
import com.app.ecovidx.data.model.Category

class CategoryAdapter(
    private val categoryList: List<Category>,
    private val listener: ClickItemListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var imageList = arrayOf(
        R.drawable.uncategorized,
        R.drawable.fresh,
        R.drawable.fruit,
        R.drawable.special_tag,
        R.drawable.vegetable,
        R.drawable.grocery,
        R.drawable.rice,
        R.drawable.household,
        R.drawable.bakery,
        R.drawable.food_cupboard,
        R.drawable.baby_products,
        R.drawable.chilled,
        R.drawable.soft_drink,
        R.drawable.pets
    )

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTextView: TextView = itemView.findViewById(R.id.item_name)
        var itemImage: ImageView = itemView.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.category_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val category = categoryList[position]
        holder.itemImage.setImageResource(imageList[position])
        holder.itemTextView.text = category.name

        holder.itemImage.setOnClickListener {
            listener.onCategoryItemClicked(category)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    interface ClickItemListener {
        fun onCategoryItemClicked(category: Category)
    }
}