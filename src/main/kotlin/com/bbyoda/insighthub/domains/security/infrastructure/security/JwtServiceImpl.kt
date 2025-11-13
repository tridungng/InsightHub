package com.bbyoda.insighthub.domains.security.infrastructure.security

import com.bbyoda.insighthub.domains.security.application.port.JwtService
import org.springframework.beans.factory.annotation.Value
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date

@Service("jwtService")
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

    override fun defaultTtlSeconds(): Long = ttlSeconds

}