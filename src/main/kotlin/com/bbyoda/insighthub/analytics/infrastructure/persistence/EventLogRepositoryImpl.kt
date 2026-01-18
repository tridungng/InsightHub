package com.bbyoda.insighthub.analytics.infrastructure.persistence

import com.bbyoda.insighthub.analytics.domain.repository.EventLogRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class EventLogRepositoryImpl(
    private val jpa: EventLogJpaRepository
) : EventLogRepository {

    @Transactional
    override fun tryRecord(eventKey: String): Boolean {
        if (jpa.existsByEventKey(eventKey)) return false
        jpa.save(EventLogEntity(eventKey = eventKey))
        return true
    }
}
