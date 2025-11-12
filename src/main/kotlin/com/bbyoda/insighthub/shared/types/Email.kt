package com.bbyoda.insighthub.shared.types

import java.util.regex.Pattern

@JvmInline
value class Email(val value: String) {
    init {
        require(EMAIL_REGEX.matcher(value).matches()) { "Invalid email format: $value" }
    }

    override fun toString(): String = value

    companion object {
        private val EMAIL_REGEX: Pattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )
    }
}