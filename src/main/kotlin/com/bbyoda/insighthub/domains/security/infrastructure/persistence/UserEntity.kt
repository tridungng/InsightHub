package com.bbyoda.insighthub.domains.security.infrastructure.persistence

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    val id: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false, name = "password_hash")
    val passwordHash: String,

    @Column(nullable = false, name = "first_name")
    val firstName: String,

    @Column(nullable = false, name = "last_name")
    val lastName: String,

    @Column(nullable = false)
    val status: String,

    @Column(nullable = false, name = "created_at")
    val createdAt: Instant = Instant.now()
) {
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role_name")
    var roles: MutableSet<String> = mutableSetOf()

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_permissions", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "permission")
    var permissions: MutableSet<String> = mutableSetOf()
}