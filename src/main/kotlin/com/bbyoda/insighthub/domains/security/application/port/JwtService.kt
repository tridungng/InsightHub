package com.bbyoda.insighthub.domains.security.application.port

interface JwtService {
    data class TokenSpec(
        val subject: String,
        val email: String,
        val fullName: String,
        val permissions: Set<String>,
        val ttlSeconds: Long
    )

    fun issue(spec: TokenSpec): String
    fun defaultTtlSeconds(): Long
}