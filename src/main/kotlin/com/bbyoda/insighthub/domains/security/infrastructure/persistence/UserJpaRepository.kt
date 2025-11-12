package com.bbyoda.insighthub.domains.security.infrastructure.persistence

import com.bbyoda.insighthub.domains.security.infrastructure.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, String> {
    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
}