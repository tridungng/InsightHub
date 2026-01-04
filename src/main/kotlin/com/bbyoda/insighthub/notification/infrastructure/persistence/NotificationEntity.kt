package com.bbyoda.insighthub.notification.infrastructure.persistence

import jakarta.persistence.*
import java.time.Instant

import com.bbyoda.insighthub.notification.domain.model.NotificationChannel
import com.bbyoda.insighthub.notification.domain.model.NotificationPriority
import com.bbyoda.insighthub.notification.domain.model.NotificationStatus

@Entity
@Table(name = "notifications")
class NotificationEntity(
    @Id
    val id: String,

    @Column(name = "recipient_user_id")
    val recipientUserId: String?,

    @Column(name = "recipient_address")
    val recipientAddress: String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val channel: NotificationChannel,

    @Column
    val subject: String?,

    @Column(columnDefinition = "TEXT", nullable = false)
    val body: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: NotificationStatus,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val priority: NotificationPriority,

    @Column(nullable = false, name = "created_at")
    val createdAt: Instant,

    @Column(name = "sent_at")
    val sentAt: Instant?,

    @Column(name = "read_at")
    val readAt: Instant?
) {
    constructor() : this(
        id = "",
        recipientUserId = "",
        recipientAddress = "",
        channel = NotificationChannel.EMAIL,
        subject = "",
        body = "",
        status = NotificationStatus.PENDING,
        priority = NotificationPriority.NORMAL,
        createdAt = Instant.now(),
        sentAt = null,
        readAt = null
    )
}
