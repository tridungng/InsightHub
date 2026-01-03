package com.bbyoda.insighthub.notification.infrastructure.persistence

import com.bbyoda.insighthub.notification.domain.model.*
import com.bbyoda.insighthub.notification.domain.repository.NotificationRepository
import com.bbyoda.insighthub.shared.types.UserId
import org.springframework.stereotype.Repository

@Repository
class NotificationRepositoryImpl(
    private val jpa: NotificationJpaRepository
) : NotificationRepository {

    override fun save(notification: Notification): Notification =
        toDomain(jpa.save(toEntity(notification)))

    override fun findById(id: String): Notification? =
        jpa.findById(id).orElse(null)?.let(::toDomain)

    override fun findPending(limit: Int): List<Notification> =
        jpa.findTopByStatus(NotificationStatus.PENDING, limit).map(::toDomain)

    override fun findByUserId(userId: String, limit: Int): List<Notification> =
        jpa.findByRecipientUserIdOrderByCreatedAtDesc(userId).take(limit).map(::toDomain)

    private fun toEntity(n: Notification) = NotificationEntity(
        id = n.id,
        recipientUserId = n.recipientUserId?.value,
        recipientAddress = n.recipientAddress,
        channel = n.channel,
        subject = n.subject,
        body = n.body,
        status = n.status,
        priority = n.priority,
        createdAt = n.createdAt,
        sentAt = n.sentAt,
        readAt = n.readAt
    )

    private fun toDomain(e: NotificationEntity) = Notification(
        id = e.id,
        recipientUserId = e.recipientUserId?.let(::UserId),
        recipientAddress = e.recipientAddress,
        channel = e.channel,
        subject = e.subject,
        body = e.body,
        status = e.status,
        priority = e.priority,
        createdAt = e.createdAt,
        sentAt = e.sentAt,
        readAt = e.readAt
    )
}
