package com.bbyoda.insighthub.notification.application.dto

import com.bbyoda.insighthub.notification.domain.model.Notification

data class NotificationDto(
    val id: String,
    val recipientUserId: String?,
    val recipientAddress: String?,
    val channel: String,
    val subject: String?,
    val body: String,
    val status: String,
    val priority: String,
    val createdAt: String,
    val sentAt: String?,
    val readAt: String?
) {

    companion object {
        fun fromDomain(domain: Notification): NotificationDto {
            return NotificationDto(
                id = domain.id,
                recipientUserId = domain.recipientUserId?.toString(),
                recipientAddress = domain.recipientAddress,
                channel = domain.channel.name,
                subject = domain.subject,
                body = domain.body,
                status = domain.status.name,
                priority = domain.priority.name,
                createdAt = domain.createdAt.toString(),
                sentAt = domain.sentAt?.toString(),
                readAt = domain.readAt?.toString()
            )
        }
    }
}