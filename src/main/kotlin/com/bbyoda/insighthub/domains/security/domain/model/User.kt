package com.bbyoda.insighthub.domains.security.domain.model

import java.time.Instant
import org.springframework.security.crypto.password.PasswordEncoder

import com.bbyoda.insighthub.domains.security.domain.Role
import com.bbyoda.insighthub.domains.security.domain.event.PasswordChanged
import com.bbyoda.insighthub.domains.security.domain.event.RoleAssigned
import com.bbyoda.insighthub.domains.security.domain.event.UserCreated
import com.bbyoda.insighthub.shared.kernel.AggregateRoot
import com.bbyoda.insighthub.shared.types.Email
import com.bbyoda.insighthub.shared.types.UserId

class User(
    override val id: UserId,
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

    fun permissions(): Set<Permission> =
        roles.flatMap { it.permissions }.toSet()

    fun verifyPassword(encodedPassword: String, encoder: PasswordEncoder): Boolean {
        return encoder.matches(encodedPassword, passwordHash)
    }

    fun changePassword(oldPassword: String, newPassword: String, encoder: PasswordEncoder) {
        require(encoder.matches(oldPassword, passwordHash)) { "Old password does not match" }
        passwordHash = encoder.encode(newPassword)
        addDomainEvent(PasswordChanged(id))
    }

    fun passwordHash(): String = passwordHash

}