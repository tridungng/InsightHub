package com.bbyoda.insighthub.identity.application.dto

sealed class IdentityError(val code: String, val message: String) {
    object EmailAlreadyExists : IdentityError("EMAIL_EXISTS", "Email already exists")
    object InvalidCredentials : IdentityError("INVALID_CREDENTIALS", "Invalid credentials")
    object UserNotFound : IdentityError("USER_NOT_FOUND", "User not found")
    object Forbidden : IdentityError("FORBIDDEN", "Forbidden")
}