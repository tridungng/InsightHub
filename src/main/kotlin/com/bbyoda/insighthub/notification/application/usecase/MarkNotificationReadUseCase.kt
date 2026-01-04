package com.bbyoda.insighthub.notification.application.usecase

import com.bbyoda.insighthub.shared.kernel.Result
import com.bbyoda.insighthub.notification.application.dto.NotificationDto
import com.bbyoda.insighthub.notification.application.dto.NotificationError
import com.bbyoda.insighthub.notification.domain.repository.NotificationRepository

class MarkNotificationReadUseCase(
    private val repository: NotificationRepository
) {
    fun execute(id: String): Result<NotificationDto, NotificationError> {
        val n = repository.findById(id) ?: return Result.failure(NotificationError.NotFound)
        n.markRead()
        val saved = repository.save(n)
        return Result.success(NotificationDto.fromDomain(saved))
    }
}
