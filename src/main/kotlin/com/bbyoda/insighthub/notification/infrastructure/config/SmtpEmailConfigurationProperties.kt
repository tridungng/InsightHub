package com.bbyoda.insighthub.notification.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("notification.email")
data class SmtpEmailConfigurationProperties(
    var from: String = "no-reply@localhost",
    var defaultSubject: String = "(no subject)",
    var maxRetries: Int = 3,
    var timeoutMs: Long = 10000
)