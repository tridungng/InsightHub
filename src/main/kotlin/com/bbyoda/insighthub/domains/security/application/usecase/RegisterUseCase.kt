package com.bbyoda.insighthub.domains.security.application.usecase

import com.bbyoda.insighthub.domains.security.application.dto.IdentityError
import com.bbyoda.insighthub.domains.security.application.dto.UserDto
import com.bbyoda.insighthub.domains.security.application.port.EventPublisher
import com.bbyoda.insighthub.domains.security.domain.model.User
import com.bbyoda.insighthub.domains.security.domain.repository.UserRepository

import com.bbyoda.insighthub.domains.security.domain.service.PasswordPolicy
import com.bbyoda.insighthub.shared.types.Email
import com.bbyoda.insighthub.shared.types.UserId
import com.bbyoda.insighthub.shared.kernel.Result

class RegisterUserUseCase(
    private val userRepository: UserRepository,
    private val passwordPolicy: PasswordPolicy,
    private val eventPublisher: EventPublisher
) {

    fun execute(email: String, password: String, firstName: String, lastName: String): Result<UserDto, IdentityError> {
        val emailVO = Email(email)
        if (userRepository.existsByEmail(emailVO)) return Result.failure(IdentityError.EmailAlreadyExists)

        val user = User(
            id = UserId.generate(),
            email = emailVO,
            passwordHash = passwordPolicy.encode(password),
            firstName = firstName,
            lastName = lastName
        )

        val saved = userRepository.save(user)
        eventPublisher.publish(saved.getDomainEvents())
        saved.clearDomainEvents()

        return Result.success(UserDto.fromDomain(saved))
    }
}