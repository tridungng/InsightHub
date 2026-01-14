package com.bbyoda.insighthub.analytics.domain.repository

interface EventLogRepository {
    /**
     * Idempotency guard.
     * Returns true if event is new and recorded, false if already processed.
     */
    fun tryRecord(eventKey: String): Boolean
}