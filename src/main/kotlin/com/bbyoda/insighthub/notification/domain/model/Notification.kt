package com.bbyoda.insighthub.notification.domain.model

import com.bbyoda.insighthub.notification.domain.event.NotificationCreated
import com.bbyoda.insighthub.notification.domain.event.NotificationFailed
import com.bbyoda.insighthub.notification.domain.event.NotificationSent
import com.bbyoda.insighthub.shared.kernel.AggregateRoot
import com.bbyoda.insighthub.shared.types.UserId
import java.time.Instant
import java.util.UUID

class Notification(
    val id: String = UUID.randomUUID().toString(),
    val recipientUserId: UserId?,
    val recipientAddress: String?,    // e-mail, phone, webhook URL, etc.
    var channel: NotificationChannel,
    var subject: String?,
    var body: String,
    var status: NotificationStatus = NotificationStatus.PENDING,
    var priority: NotificationPriority = NotificationPriority.NORMAL,
    val createdAt: Instant = Instant.now(),
    var sentAt: Instant? = null,
    var readAt: Instant? = null,
    val metadata: Map<String, String> = emptyMap()
) : AggregateRoot<String>() {

    init {
        addDomainEvent(
            NotificationCreated(
                notificationId = id,
                channel = channel,
                createdAt = createdAt
            )
        )
    }

    fun markSent() {
        status = NotificationStatus.SENT
        sentAt = Instant.now()
        addDomainEvent(NotificationSent(id, channel, sentAt!!))
    }

    fun markFailed(reason: String) {
        status = NotificationStatus.FAILED
        addDomainEvent(NotificationFailed(id, channel, reason))
    }

    fun markRead() {
        if (status == NotificationStatus.SENT) {
            status = NotificationStatus.READ
            readAt = Instant.now()
        }
    }
}
