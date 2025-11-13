package com.bbyoda.insighthub.identity.interfaces

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.bbyoda.insighthub.shared.kernel.Result
import com.bbyoda.insighthub.identity.application.dto.IdentityError
import com.bbyoda.insighthub.identity.application.usecase.ChangePasswordUseCase
import com.bbyoda.insighthub.identity.application.usecase.LoginUseCase
import com.bbyoda.insighthub.identity.application.usecase.RegisterUserUseCase

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val registerUser: RegisterUserUseCase,
    private val login: LoginUseCase,
    private val changePassword: ChangePasswordUseCase
) {
    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ResponseEntity<*> =
        when (val res = registerUser.execute(req.email, req.password, req.firstName, req.lastName)) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<*> =
        when (val res = login.execute(req.email, req.password)) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @PostMapping("/change-password")
    fun changePassword(@RequestBody req: ChangePasswordRequest): ResponseEntity<*> =
        when (val res =
            changePassword.execute(ChangePasswordUseCase.Cmd(req.userId, req.oldPassword, req.newPassword))) {
            is Result.Success -> ResponseEntity.noContent().build<Any>()
            is Result.Failure -> toProblem(res.error)
        }

    data class RegisterRequest(val email: String, val password: String, val firstName: String, val lastName: String)
    data class LoginRequest(val email: String, val password: String)
    data class ChangePasswordRequest(val userId: String, val oldPassword: String, val newPassword: String)

    private fun toProblem(err: IdentityError): ResponseEntity<*> =
        ResponseEntity.status(
            when (err) {
                IdentityError.EmailAlreadyExists -> 409
                IdentityError.InvalidCredentials -> 401
                IdentityError.UserNotFound -> 404
                IdentityError.Forbidden -> 403
            }
        ).body(mapOf("error" to err.code, "message" to err.message))
}