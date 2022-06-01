package com.app.ecovidx.data.model.order

data class ShippingDetail(
    val _shipping_address_1: String,
    val _shipping_city: String,
    val _shipping_country: String,
    val _shipping_first_name: String,
    val _shipping_last_name: String,
    val _shipping_phone: String,
    val _shipping_postcode: String?
)