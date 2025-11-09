package com.bbyoda.insighthub.security.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Id val id: UUID = UUID.randomUUID(),
    @Column(nullable = false, unique = true)
    var username: String,
    var password: String,
    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER,
)