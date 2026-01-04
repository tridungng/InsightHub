package com.bbyoda.insighthub.notification.application.dto

sealed class EmailSendError {
    data class InvalidRecipient(val reason: String) : EmailSendError()
    data class ProviderFailure(val provider: String, val message: String?) : EmailSendError()
}