package com.bbyoda.insighthub.notification.application.port

import com.bbyoda.insighthub.notification.application.dto.EmailSendError
import com.bbyoda.insighthub.shared.kernel.Result

interface EmailProvider {
    fun send(to: String, subject: String?, body: String): Result<Unit, EmailSendError>
}