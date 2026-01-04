package com.bbyoda.insighthub.notification.application.usecase

import com.bbyoda.insighthub.notification.application.dto.NotificationDto
import com.bbyoda.insighthub.notification.application.port.EventPublisher
import com.bbyoda.insighthub.notification.domain.model.Notification
import com.bbyoda.insighthub.notification.domain.model.NotificationChannel
import com.bbyoda.insighthub.notification.domain.model.NotificationPriority
import com.bbyoda.insighthub.notification.domain.repository.NotificationRepository
import com.bbyoda.insighthub.shared.kernel.Result
import com.bbyoda.insighthub.shared.types.UserId
import org.springframework.stereotype.Service

class CreateNotificationUseCase(
    private val repository: NotificationRepository,
    private val events: EventPublisher
) {
    data class Cmd(
        val recipientUserId: String?,
        val recipientAddress: String?,
        val channel: NotificationChannel,
        val subject: String?,
        val body: String,
        val priority: NotificationPriority = NotificationPriority.NORMAL,
        val metadata: Map<String, String> = emptyMap()
    )

    fun execute(cmd: Cmd): Result<NotificationDto, Nothing> {
        val notification = Notification(
            recipientUserId = cmd.recipientUserId?.let(::UserId),
            recipientAddress = cmd.recipientAddress,
            channel = cmd.channel,
            subject = cmd.subject,
            body = cmd.body,
            priority = cmd.priority,
            metadata = cmd.metadata
        )

        val saved = repository.save(notification)
        events.publish(saved.domainEvents)
        saved.clearDomainEvents()

        return Result.success(NotificationDto.fromDomain(saved))
    }
}
