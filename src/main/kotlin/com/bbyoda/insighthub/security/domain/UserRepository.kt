package com.bbyoda.insighthub.security.domain

interface UserRepository {
    fun save(user: User): User
    
    fun findByUsername(username: String): User?
}