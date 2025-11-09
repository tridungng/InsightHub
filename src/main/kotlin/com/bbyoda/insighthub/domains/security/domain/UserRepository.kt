package com.bbyoda.insighthub.domains.security.domain

interface UserRepository {
    fun save(user: User): User

    fun findByUsername(username: String): User?
}