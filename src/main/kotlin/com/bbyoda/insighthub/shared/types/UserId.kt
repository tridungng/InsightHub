package com.bbyoda.insighthub.shared.types

import java.util.UUID

@JvmInline
value class UserId(val value: String) {
    companion object {
        fun generate(): UserId = UserId(UUID.randomUUID().toString())
    }

    override fun toString(): String = value
}