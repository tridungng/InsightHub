package com.bbyoda.insighthub.domains.security.infrastructure.security

import com.bbyoda.insighthub.domains.security.domain.service.PasswordPolicy
import org.springframework.stereotype.Component

@Component
class PasswordPolicyImpl(
    private val encoder: BCryptPasswordEncoderAdapter
) : PasswordPolicy {

    override fun validate(raw: String): Boolean {
        return PASSWORD_REGEX.matches(raw)
    }

    override fun encode(raw: String): String {
        require(validate(raw)) {
            """
            Password does not meet security requirements:
              • Minimum length: 8 characters
              • At least one uppercase letter
              • At least one lowercase letter
              • At least one digit
              • At least one special character
            """.trimIndent()
        }
        
        return encoder.encode(raw)
    }

    override fun matches(raw: String, encoded: String): Boolean {
        return encoder.matches(raw, encoded)
    }

    companion object {
        private val PASSWORD_REGEX =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
    }
}