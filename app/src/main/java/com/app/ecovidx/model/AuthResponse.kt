package com.app.ecovidx.model

data class AuthResponse(
    val access_token: String,
    val token_type: String,
    val user: User,
    val error: String
)