package com.bbyoda.insighthub.identity.infrastructure.security

import java.time.Instant
import java.util.Date
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value

import com.bbyoda.insighthub.identity.application.port.JwtService

@Service
class JwtServiceImpl(
    @Value("\${security.jwt.secret}") private val secretBase64: String,
    @Value("\${security.jwt.ttl-seconds:3600}") private val ttlSeconds: Long,
    @Value("\${security.jwt.issuer:insighthub}") private val issuer: String
) : JwtService {

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretBase64)) }

    override fun issue(spec: JwtService.TokenSpec): String {
        val now = Instant.now()
        val exp = now.plusSeconds(spec.ttlSeconds)
        return Jwts.builder()
            .setSubject(spec.subject)
            .setIssuer(issuer)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(exp))
            .claim("email", spec.email)
            .claim("name", spec.fullName)
            .claim("permissions", spec.permissions)
            .signWith(key)
            .compact()
    }

    override fun validate(token: String): Boolean = try {
        val claims = parseClaims(token)
        val exp = claims.expiration.toInstant()
        Instant.now().isBefore(exp)
    } catch (e: Exception) {
        false
    }

    override fun extractUserId(token: String): String? =
        runCatching { parseClaims(token).subject }.getOrNull()

    override fun extractEmail(token: String): String? =
        runCatching { parseClaims(token)["email"] as? String }.getOrNull()

    override fun extractPermissions(token: String): Set<String> =
        runCatching {
            val list = parseClaims(token)["permissions"] as? Collection<*>
            list?.mapNotNull { it?.toString() }?.toSet() ?: emptySet()
        }.getOrDefault(emptySet())

    private fun parseClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

    override fun defaultTtlSeconds(): Long = ttlSeconds

}