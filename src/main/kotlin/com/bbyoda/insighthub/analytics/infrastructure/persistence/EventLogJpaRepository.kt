package com.bbyoda.insighthub.analytics.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface EventLogJpaRepository : JpaRepository<EventLogEntity, Long> {
    fun existsByEventKey(eventKey: String): Boolean
}
