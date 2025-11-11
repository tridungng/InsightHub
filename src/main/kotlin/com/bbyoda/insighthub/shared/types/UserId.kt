package com.bbyoda.insighthub.shared.types

import java.util.UUID

@JvmInline
value class UserId(val value: UUID) {
    companion object {
        fun generate() = UserId(UUID.randomUUID())
        fun from(string: String) = UserId(UUID.fromString(string))
    }

    override fun toString(): String = value.toString()
}