package com.app.ecovidx.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class Cart(
    @ColumnInfo(name = "product_name")
    var product: String,

    @ColumnInfo(name = "quantity")
    var quantity: Int,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "price")
    var price: Double

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}