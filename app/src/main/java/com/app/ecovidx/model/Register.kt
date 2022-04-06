package com.app.ecovidx.model

data class Register(
    val password_confirmation: String,
    val user_email: String,
    val user_login: String,
    val user_pass: String
)