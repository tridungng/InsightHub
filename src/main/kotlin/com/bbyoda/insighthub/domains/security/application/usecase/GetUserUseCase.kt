package com.bbyoda.insighthub.domains.security.application.usecase

import com.bbyoda.insighthub.domains.security.application.dto.IdentityError
import com.bbyoda.insighthub.domains.security.application.dto.UserDto
import com.bbyoda.insighthub.domains.security.domain.repository.UserRepository
import com.bbyoda.insighthub.shared.types.UserId
import com.bbyoda.insighthub.shared.kernel.Result
import org.springframework.stereotype.Service

@Service
class GetUserUseCase(private val users: UserRepository) {
    fun byId(id: String): Result<UserDto, IdentityError> {
        val user = users.findById(UserId(id)) ?: return Result.failure(IdentityError.UserNotFound)
        return Result.success(UserDto.fromDomain(user))
    }
}