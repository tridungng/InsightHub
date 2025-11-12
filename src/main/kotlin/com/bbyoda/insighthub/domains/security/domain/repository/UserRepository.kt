package com.bbyoda.insighthub.domains.security.domain.repository

import com.bbyoda.insighthub.domains.security.domain.model.User
import com.bbyoda.insighthub.shared.types.Email
import com.bbyoda.insighthub.shared.types.UserId

interface UserRepository {
    fun save(user: User): User
    fun findById(id: UserId): User?
    fun findByEmail(email: Email): User?
    fun existsByEmail(email: Email): Boolean
}