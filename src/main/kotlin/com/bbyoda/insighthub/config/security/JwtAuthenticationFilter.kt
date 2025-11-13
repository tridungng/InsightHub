package com.bbyoda.insighthub.config.security

import com.bbyoda.insighthub.identity.application.port.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

import com.bbyoda.insighthub.identity.domain.model.Permission

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization") ?: ""
        if (!header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = header.removePrefix("Bearer ").trim()

        if (!jwtService.validate(token)) {
            filterChain.doFilter(request, response)
            return
        }

        val userId = jwtService.extractUserId(token) ?: run {
            filterChain.doFilter(request, response)
            return
        }

        val email = jwtService.extractEmail(token).orEmpty()
        val permissions = jwtService.extractPermissions(token)
            .mapNotNull { runCatching { Permission.valueOf(it) }.getOrNull() }
            .toSet()

        val principal = UserPrincipal(
            userId = userId,
            email = email,
            firstName = "",
            lastName = "",
            permissions = permissions
        )

        val auth = UsernamePasswordAuthenticationToken(principal, null, emptyList())
        auth.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = auth

        filterChain.doFilter(request, response)
    }
}