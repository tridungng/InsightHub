package com.bbyoda.insighthub.identity.infrastructure.event

import com.bbyoda.insighthub.identity.application.port.EventPublisher
import com.bbyoda.insighthub.shared.kernel.DomainEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component("identityPublisher")
class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : EventPublisher {

    override fun publish(events: Collection<DomainEvent>) {
        events.forEach { ev ->
            val topic = "identity.${ev::class.simpleName}"
            kafkaTemplate.send(topic, ev)
        }
    }

}