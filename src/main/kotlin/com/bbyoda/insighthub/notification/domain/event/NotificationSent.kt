package com.bbyoda.insighthub.notification.domain.event

import java.time.Instant

import com.bbyoda.insighthub.notification.domain.model.NotificationChannel
import com.bbyoda.insighthub.shared.kernel.DomainEvent

data class NotificationSent(
    val notificationId: String,
    val channel: NotificationChannel,
    val sentAt: Instant,
    override val occurredAt: Instant = sentAt
) : DomainEvent