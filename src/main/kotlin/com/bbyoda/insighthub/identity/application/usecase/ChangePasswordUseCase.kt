package com.bbyoda.insighthub.identity.application.usecase

import com.bbyoda.insighthub.identity.application.dto.IdentityError
import com.bbyoda.insighthub.identity.domain.repository.UserRepository
import com.bbyoda.insighthub.identity.domain.service.PasswordPolicy
import com.bbyoda.insighthub.shared.kernel.Result
import com.bbyoda.insighthub.shared.types.UserId
import org.springframework.stereotype.Service

@Service
class ChangePasswordUseCase(
    private val users: UserRepository,
    private val passwordPolicy: PasswordPolicy
) {
    data class Cmd(val userId: String, val oldPassword: String, val newPassword: String)

    fun execute(cmd: Cmd): Result<Unit, IdentityError> {
        val user = users.findById(UserId(cmd.userId)) ?: return Result.failure(IdentityError.UserNotFound)
        user.changePassword(cmd.oldPassword, cmd.newPassword, passwordPolicy)
        users.save(user)
        return Result.success(Unit)
    }
}