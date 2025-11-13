package com.bbyoda.insighthub.domains.security.application.usecase

import com.bbyoda.insighthub.domains.security.application.dto.AuthenticationResult
import com.bbyoda.insighthub.domains.security.application.dto.IdentityError
import com.bbyoda.insighthub.domains.security.application.port.EventPublisher
import com.bbyoda.insighthub.domains.security.application.port.JwtService
import com.bbyoda.insighthub.domains.security.domain.event.UserLoggedIn
import com.bbyoda.insighthub.domains.security.domain.repository.UserRepository
import com.bbyoda.insighthub.domains.security.domain.service.PasswordPolicy
import com.bbyoda.insighthub.shared.kernel.Result
import com.bbyoda.insighthub.shared.types.Email
import org.springframework.stereotype.Service

@Service
class LoginUseCase(
    private val users: UserRepository,
    private val jwt: JwtService,
    private val passwordPolicy: PasswordPolicy,
    private val eventPublisher: EventPublisher
) {

    fun execute(email: String, password: String): Result<AuthenticationResult, IdentityError> {
        val user = users.findByEmail(Email(email)) ?: return Result.failure(IdentityError.InvalidCredentials)
        if (!user.verifyPassword(password, passwordPolicy)) return Result.failure(IdentityError.InvalidCredentials)

        val token = jwt.issue(
            JwtService.TokenSpec(
                subject = user.id.value,
                email = user.email.value,
                fullName = "${user.firstName} ${user.lastName}",
                permissions = user.permissions().map { it.name }.toSet(),
                ttlSeconds = jwt.defaultTtlSeconds()
            )
        )

        val result = AuthenticationResult(
            accessToken = token,
            userId = user.id.value,
            email = user.email.value,
            expiresInSeconds = jwt.defaultTtlSeconds()
        )
        eventPublisher.publish(listOf(UserLoggedIn(user.id)))

        return Result.success(result)
    }
}