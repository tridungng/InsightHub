package com.bbyoda.insighthub.domains.security.infrastructure.persistence

import com.bbyoda.insighthub.domains.security.domain.model.User
import com.bbyoda.insighthub.domains.security.domain.repository.UserRepository
import com.bbyoda.insighthub.shared.types.Email
import com.bbyoda.insighthub.shared.types.UserId

class UserRepositoryImpl(private val jpa: UserJpaRepository, private val mapper: UserMapper) : UserRepository {

    override fun save(user: User): User = mapper.toDomain(jpa.save(mapper.toEntity(user)))

    override fun findById(id: UserId): User? = jpa.findById(id.value).orElse(null)?.let(mapper::toDomain)

    override fun findByEmail(email: Email): User? = jpa.findByEmail(email.value)?.let(mapper::toDomain)

    override fun existsByEmail(email: Email): Boolean = jpa.existsByEmail(email.value)
}
