package com.bbyoda.insighthub.notification.application.usecase

import com.bbyoda.insighthub.notification.application.dto.NotificationDto
import com.bbyoda.insighthub.notification.domain.repository.NotificationRepository

class GetNotificationsUseCase(
    private val repository: NotificationRepository
) {
    fun byUser(userId: String, limit: Int = 50): List<NotificationDto> =
        repository.findByUserId(userId, limit).map(NotificationDto::fromDomain)
}
