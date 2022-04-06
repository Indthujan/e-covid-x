package com.app.ecovidx.model

data class Password(
    val email: String,
    val confirm_password: String,
    val hash: String,
    val message: String,
    val new_password: String,
    val otp: String,
    val user_id: Int
)