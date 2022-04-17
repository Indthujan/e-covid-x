package com.app.ecovidx.data.model

data class User(
    val ID: Int,
    val display_name: String,
    val reset_passwwword_hash: String,
    val user_activation_key: String,
    val user_email: String,
    val user_login: String,
    val user_nicename: String,
    val user_pass: String,
    val user_registered: String,
    val user_status: Int,
    val user_url: String
)