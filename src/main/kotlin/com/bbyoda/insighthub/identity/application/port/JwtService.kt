package com.bbyoda.insighthub.identity.application.port

interface JwtService {
    data class TokenSpec(
        val subject: String,
        val email: String,
        val fullName: String,
        val permissions: Set<String>,
        val ttlSeconds: Long
    )

    fun issue(spec: TokenSpec): String
    fun validate(token: String): Boolean
    fun extractUserId(token: String): String?
    fun extractEmail(token: String): String?
    fun extractPermissions(token: String): Set<String>
    fun defaultTtlSeconds(): Long
}