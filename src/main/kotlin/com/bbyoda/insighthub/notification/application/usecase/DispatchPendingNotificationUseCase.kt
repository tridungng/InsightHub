package com.bbyoda.insighthub.notification.application.usecase

import com.bbyoda.insighthub.notification.application.port.ChannelSender
import com.bbyoda.insighthub.notification.application.port.EventPublisher
import com.bbyoda.insighthub.notification.domain.repository.NotificationRepository

class DispatchPendingNotificationsUseCase(
    private val repository: NotificationRepository,
    private val channelSenders: List<ChannelSender>,
    private val events: EventPublisher
) {

    fun execute(batchSize: Int = 100) {
        val pending = repository.findPending(batchSize)
        pending.forEach { n ->
            val sender = channelSenders.firstOrNull { it.supports(n.channel) }
            if (sender == null) {
                n.markFailed("No sender for channel ${n.channel}")
            } else {
                val result = sender.send(n)
                if (result.success) {
                    n.markSent()
                } else {
                    n.markFailed(result.error ?: "Unknown error")
                }
            }

            val saved = repository.save(n)
            events.publish(saved.domainEvents)
            saved.clearDomainEvents()
        }
    }
}
