package com.bbyoda.insighthub.notification.infrastructure.config


import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SmtpEmailConfigurationProperties::class)
class NotificationConfiguration {
}