package com.bbyoda.insighthub.notification.domain.event

import java.time.Instant

import com.bbyoda.insighthub.notification.domain.model.NotificationChannel
import com.bbyoda.insighthub.shared.kernel.DomainEvent

data class NotificationFailed(
    val notificationId: String,
    val channel: NotificationChannel,
    val reason: String,
    override val occurredAt: Instant = Instant.now()
) : DomainEvent