package com.bbyoda.insighthub.analytics.infrastructure.persistence

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "analytics_event_log",
    uniqueConstraints = [UniqueConstraint(name = "uq_event_key", columnNames = ["event_key"])]
)
class EventLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "event_key", nullable = false, length = 256)
    val eventKey: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now()
) {
    constructor() : this(eventKey = "")
}
