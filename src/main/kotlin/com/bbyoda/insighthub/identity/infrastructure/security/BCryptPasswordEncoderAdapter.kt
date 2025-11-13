package com.bbyoda.insighthub.identity.infrastructure.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class BCryptPasswordEncoderAdapter {

    private val delegate = BCryptPasswordEncoder()

    fun encode(raw: String): String = delegate.encode(raw)

    fun matches(raw: String, encoded: String): Boolean = delegate.matches(raw, encoded)

}