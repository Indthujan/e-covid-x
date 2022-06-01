package com.app.ecovidx.data.model

data class Orders(
    val orders: List<OrderList>
)

data class OrderList(
    val ID: Int,
    val comment_count: Int,
    val comment_status: String,
    val guid: String,
    val menu_order: Int,
    val meta: List<Meta>,
    val ping_status: String,
    val pinged: String,
    val post_author: Int,
    val post_content: String,
    val post_content_filtered: String,
    val post_date: String,
    val post_date_gmt: String,
    val post_excerpt: String,
    val post_mime_type: String,
    val post_modified: String,
    val post_modified_gmt: String,
    val post_name: String,
    val post_parent: Int,
    val post_password: String,
    val post_status: String,
    val post_title: String,
    val post_type: String,
    val to_ping: String
)

data class Meta(
    val meta_id: Int,
    val meta_key: String,
    val meta_value: String,
    val post_id: Int
)