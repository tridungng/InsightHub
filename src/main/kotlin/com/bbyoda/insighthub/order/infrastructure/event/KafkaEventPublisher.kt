package com.bbyoda.insighthub.order.infrastructure.event

import com.bbyoda.insighthub.order.application.port.EventPublisher
import com.bbyoda.insighthub.shared.kernel.DomainEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component("orderPublisher")
class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : EventPublisher {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun publish(events: Collection<DomainEvent>) {
        events.forEach { ev ->
            val topic = "order.${ev::class.simpleName}"
            kafkaTemplate.send(topic, ev)
            log.debug("Published event {} to {}", ev, topic)
        }
    }
}