package com.app.ecovidx.data.model

data class Profile(
    val ID: Int,
    val billing_address_1: String?,
    val billing_address_2: String,
    val billing_city: String?,
    val billing_company: String?,
    val billing_phone: String?,
    val billing_postcode: String?,
    val display_name: String,
    val first_name: String?,
    val last_name: String?,
    val reset_passwwword_hash: String,
    val user_activation_key: String,
    val user_email: String,
    val user_login: String,
    val user_nicename: String,
    val user_pass: String,
    val user_registered: String,
    val user_status: Int,
    val user_url: String,
    val status: String
)