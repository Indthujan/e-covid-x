package com.app.ecovidx.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val image_url: String,
    val price: String,
    val product_id: Int,
    val product_title: String,
    val post_content: String
): Parcelable