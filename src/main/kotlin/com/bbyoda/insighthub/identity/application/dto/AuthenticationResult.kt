package com.bbyoda.insighthub.identity.application.dto

data class AuthenticationResult(
    val accessToken: String,
    val tokenType: String = "Bearer",
    val userId: String,
    val email: String,
    val expiresInSeconds: Long
)