package com.bbyoda.insighthub.domains.security.application

import com.bbyoda.insighthub.domains.security.domain.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service

@Service
class JwtService {
    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(user: User): String =
        Jwts.builder().setSubject(user.username).claim("username", user.username).claim("role", user.role.name)
            .signWith(secretKey).compact()

    fun validateToken(token: String): Boolean =
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }

    fun extractClaims(token: String): Claims =
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body

}