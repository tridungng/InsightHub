package com.bbyoda.insighthub.shared.types

@JvmInline
value class Email(val value: String) {
    init {
        require(isValid(value)) { "Invalid email format: $value" }
    }

    companion object {
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        fun isValid(email: String): Boolean = email.matches(EMAIL_REGEX)

        fun orNull(email: String): Email? = if (isValid(email)) Email(email) else null
    }

    override fun toString(): String = value
}