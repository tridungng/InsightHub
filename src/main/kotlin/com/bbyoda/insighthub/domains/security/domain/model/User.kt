package com.bbyoda.insighthub.domains.security.domain.model

import java.time.Instant

import com.bbyoda.insighthub.domains.security.domain.event.RoleAssigned
import com.bbyoda.insighthub.domains.security.domain.event.UserCreated
import com.bbyoda.insighthub.domains.security.domain.service.PasswordPolicy
import com.bbyoda.insighthub.shared.kernel.AggregateRoot
import com.bbyoda.insighthub.shared.types.Email
import com.bbyoda.insighthub.shared.types.UserId

class User(
    val id: UserId,
    val email: Email,
    private var passwordHash: String,
    var firstName: String,
    var lastName: String,
    private val roles: MutableSet<Role> = mutableSetOf(),
    var status: UserStatus = UserStatus.ACTIVE,
    val createdAt: Instant = Instant.now()
) : AggregateRoot<UserId>() {

    init {
        addDomainEvent(UserCreated(id, email.value, firstName, lastName))
    }

    fun assignRole(role: Role) {
        roles.add(role)
        addDomainEvent(RoleAssigned(id, role.name))
    }

    fun hasRole(roleName: String) = roles.any { it.name.equals(roleName, ignoreCase = true) }

    fun permissions(): Set<Permission> = roles.flatMap { it.permissions }.toSet()

    fun verifyPassword(raw: String, encoder: PasswordPolicy): Boolean = encoder.matches(raw, passwordHash)

    fun changePassword(old: String, new: String, encoder: PasswordPolicy) {
        require(encoder.matches(old, passwordHash)) { "Invalid old password" }
        passwordHash = encoder.encode(new)
    }

    fun passwordHash(): String = passwordHash

}