package com.bbyoda.insighthub.domains.security.application.dto

import com.bbyoda.insighthub.domains.security.domain.model.User

data class UserDto(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val status: String,
    val permissions: Set<String>
) {
    companion object {
        fun fromDomain(u: User) = UserDto(
            id = u.id.value,
            email = u.email.value,
            firstName = u.firstName,
            lastName = u.lastName,
            status = u.status.name,
            permissions = u.permissions().map { it.name }.toSet()
        )
    }
}