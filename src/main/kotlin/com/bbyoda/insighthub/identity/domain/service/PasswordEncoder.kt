package com.bbyoda.insighthub.identity.domain.service

interface PasswordEncoder {

    fun encode(raw: String): String

    fun matches(raw: String, encoded: String): Boolean

}