package com.bbyoda.insighthub.domains.security.interfaces

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.bbyoda.insighthub.shared.kernel.Result
import com.bbyoda.insighthub.domains.security.application.dto.IdentityError
import com.bbyoda.insighthub.domains.security.application.usecase.AssignRoleUseCase
import com.bbyoda.insighthub.domains.security.application.usecase.GetUserUseCase

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val getUser: GetUserUseCase,
    private val assignRole: AssignRoleUseCase
) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<*> =
        when (val res = getUser.byId(id)) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @PostMapping("/{id}/roles/{roleName}")
    fun addRole(
        @PathVariable id: String,
        @PathVariable roleName: String,
        @RequestBody body: AssignRoleBody? = null
    ): ResponseEntity<*> =
        when (val res = assignRole.execute(AssignRoleUseCase.Cmd(id, roleName, body?.permissions ?: emptySet()))) {
            is Result.Success -> ResponseEntity.noContent().build<Any>()
            is Result.Failure -> toProblem(res.error)
        }

    data class AssignRoleBody(val permissions: Set<String> = emptySet())

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