package com.bbyoda.insighthub.app.configuration.security

import com.bbyoda.insighthub.domains.security.domain.model.Permission
import java.io.Serializable

data class UserPrincipal(
    val userId: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val permissions: Set<Permission>
) : Serializable {

    val fullName: String
        get() = "$firstName $lastName"

    fun hasPermission(permission: Permission): Boolean {
        return permissions.contains(permission)
    }

    fun hasAnyPermission(vararg permissions: Permission): Boolean {
        return permissions.any { this.permissions.contains(it) }
    }

    fun hasAllPermissions(vararg permissions: Permission): Boolean {
        return permissions.all { this.permissions.contains(it) }
    }

    fun isAdmin(): Boolean {
        return hasPermission(Permission.SYSTEM_ADMIN)
    }

    override fun toString(): String {
        return "UserPrincipal(userId=$userId, email=$email, fullName=$fullName, permissions=${permissions.size})"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}