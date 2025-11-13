package com.bbyoda.insighthub.domains.security.application.usecase

import com.bbyoda.insighthub.domains.security.application.dto.IdentityError
import com.bbyoda.insighthub.domains.security.domain.model.Role
import com.bbyoda.insighthub.domains.security.domain.repository.UserRepository
import com.bbyoda.insighthub.shared.types.UserId
import com.bbyoda.insighthub.shared.kernel.Result

class AssignRoleUseCase(private val users: UserRepository) {
    data class Cmd(val userId: String, val roleName: String, val permissions: Set<String> = emptySet())

    fun execute(cmd: Cmd): Result<Unit, IdentityError> {
        val user = users.findById(UserId(cmd.userId)) ?: return Result.failure(IdentityError.UserNotFound)
        val perms = cmd.permissions.mapNotNull {
            runCatching {
                com.bbyoda.insighthub.domains.security.domain.model.Permission.valueOf(it)
            }.getOrNull()
        }.toSet()
        user.assignRole(Role(cmd.roleName, perms))
        users.save(user)

        return Result.success(Unit)
    }
}