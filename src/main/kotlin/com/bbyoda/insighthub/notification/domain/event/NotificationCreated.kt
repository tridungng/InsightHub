package com.bbyoda.insighthub.notification.domain.event

import java.time.Instant

import com.bbyoda.insighthub.notification.domain.model.NotificationChannel
import com.bbyoda.insighthub.shared.kernel.DomainEvent

data class NotificationCreated(
    val notificationId: String,
    val channel: NotificationChannel,
    override val occurredAt: Instant = Instant.now(),
    val createdAt: Instant = occurredAt
) : DomainEvent {
}