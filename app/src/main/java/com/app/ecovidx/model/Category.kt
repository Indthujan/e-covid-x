package com.app.ecovidx.model

data class Category(
    val count: Int,
    val description: String,
    val name: String,
    val parent: Int,
    val slug: String,
    val taxonomy: String,
    val term_group: Int,
    val term_id: Int,
    val term_taxonomy_id: Int
)