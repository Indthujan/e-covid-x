package com.app.ecovidx.data.model

data class Token(
    val access_token: String,
    val expires_in: Long,
    val token_type: String
)