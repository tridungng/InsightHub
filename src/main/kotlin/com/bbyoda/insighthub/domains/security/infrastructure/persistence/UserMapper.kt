package com.bbyoda.insighthub.domains.security.infrastructure.persistence

import com.bbyoda.insighthub.domains.security.domain.Role
import com.bbyoda.insighthub.domains.security.domain.model.Permission
import com.bbyoda.insighthub.domains.security.domain.model.User
import com.bbyoda.insighthub.domains.security.domain.model.UserStatus
import com.bbyoda.insighthub.domains.security.infrastructure.UserEntity
import com.bbyoda.insighthub.shared.types.Email
import com.bbyoda.insighthub.shared.types.UserId
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toEntity(domain: User): UserEntity =
        UserEntity(
            id = domain.id.value,
            email = domain.email.value,
            passwordHash = domain.passwordHash(),
            firstName = domain.firstName,
            lastName = domain.lastName,
            status = domain.status.name,
            createdAt = domain.createdAt
        ).apply {
            roles = domainPermissionsToRoleNames(domain)
            permissions = domain.permissions().map { it.name }.toMutableSet()
        }

    fun toDomain(entity: UserEntity): User {
        val user = User(
            id = UserId(entity.id),
            email = Email(entity.email),
            passwordHash = entity.passwordHash,
            firstName = entity.firstName,
            lastName = entity.lastName,
            status = UserStatus.valueOf(entity.status),
            roles = entity.roles.map { Role(it) }.toMutableSet()
        )

        return user
    }

    private fun domainPermissionsToRoleNames(domain: User): MutableSet<String> =
        domainPermissionsByRole(domain).keys.toMutableSet()

    private fun domainPermissionsByRole(domain: User): Map<String, Set<Permission>> =
        domainPermissions(domain)
    
    private fun domainPermissions(domain: User): Map<String, Set<Permission>> =
        domain.permissions().groupBy { "AGGREGATED" }.mapValues { it.value.toSet() }
}