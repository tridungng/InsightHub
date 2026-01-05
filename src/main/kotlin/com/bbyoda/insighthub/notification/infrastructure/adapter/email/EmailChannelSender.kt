package com.bbyoda.insighthub.notification.infrastructure.adapter.email

import com.bbyoda.insighthub.notification.application.dto.EmailSendError
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import com.bbyoda.insighthub.shared.kernel.Result
import com.bbyoda.insighthub.notification.application.port.ChannelSender
import com.bbyoda.insighthub.notification.application.port.EmailProvider
import com.bbyoda.insighthub.notification.domain.model.Notification
import com.bbyoda.insighthub.notification.domain.model.NotificationChannel

@Component
class EmailChannelSender(private val emailProvider: EmailProvider) : ChannelSender {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun supports(channel: NotificationChannel): Boolean = channel == NotificationChannel.EMAIL

    override fun send(notification: Notification): ChannelSender.ChannelResult {
        val to = notification.recipientAddress
            ?: return ChannelSender.ChannelResult(false, "Missing recipient address")

        return when (val res = emailProvider.send(to, notification.subject, notification.body)) {
            is Result.Success -> {
                log.info("Email sent to $to for notification ${notification.id}")
                ChannelSender.ChannelResult(true)
            }

            is Result.Failure -> ChannelSender.ChannelResult(
                success = false,
                error = when (val e = res.error) {
                    is EmailSendError.InvalidRecipient -> e.reason
                    is EmailSendError.ProviderFailure ->
                        "Email provider ${e.provider} failed: ${e.message ?: "unknown"}"
                }
            )
        }
    }
}
