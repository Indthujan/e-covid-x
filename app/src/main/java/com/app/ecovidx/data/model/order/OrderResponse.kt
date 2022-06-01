package com.app.ecovidx.data.model.order

data class OrderResponse(
    val payment_url: String,
    val sucsss: String,
    val toatal: Double,
    val order_id: Int,
    val data: List<Data>
)
//data class Ooorder(
//    val data: List<Data>
//)

data class Data(
    val ID: Int,
    val post_status: String
)