package com.bbyoda.insighthub.domains.security.domain.service

interface PasswordPolicy {
    fun validate(raw: String): Boolean
    fun encode(raw: String): String
    fun matches(raw: String, encoded: String): Boolean
}