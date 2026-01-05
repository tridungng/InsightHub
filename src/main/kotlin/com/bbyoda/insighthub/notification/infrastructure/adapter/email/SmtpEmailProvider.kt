package com.bbyoda.insighthub.notification.infrastructure.adapter.email

import org.springframework.stereotype.Component
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

import com.bbyoda.insighthub.notification.application.port.EmailProvider
import com.bbyoda.insighthub.notification.application.dto.EmailSendError
import com.bbyoda.insighthub.notification.infrastructure.config.SmtpEmailConfigurationProperties
import com.bbyoda.insighthub.shared.kernel.Result
import org.springframework.mail.MailException


@Component
@ConditionalOnProperty(
    prefix = "notification.email",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = true
)
class SmtpEmailProvider(
    private val mailSender: JavaMailSender,
    private val emailConfig: SmtpEmailConfigurationProperties
) : EmailProvider {

    override fun send(
        to: String,
        subject: String?,
        body: String
    ): Result<Unit, EmailSendError> {

        if (to.isBlank()) {
            return Result.failure(
                EmailSendError.InvalidRecipient("Recipient address is blank")
            )
        }
        val message = SimpleMailMessage().apply {
            from = emailConfig.from
            setTo(to)
            this.subject = subject ?: emailConfig.defaultSubject
            text = body
        }

        return try {
            mailSender.send(message)
            Result.success(Unit)
        } catch (ex: MailException) {
            Result.failure(
                EmailSendError.ProviderFailure(
                    provider = PROVIDER,
                    message = ex.message ?: "Unknown SMTP error"
                )
            )
        }
    }

    private companion object {
        const val PROVIDER = "smtp"
    }
}
