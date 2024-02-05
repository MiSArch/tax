package org.misarch.tax.event

import io.dapr.client.DaprClient
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component

/**
 * Publisher for dapr events
 */
@Component
class EventPublisher(private val client: DaprClient) {

    /**
     * Publishes an event via the Dapr client.
     * Uses the pubsub component.
     *
     * @param topic the topic to publish to, automatically prefixed with "tax/"
     * @param message the message to publish
     */
    suspend fun publishEvent(topic: String, message: Any) {
        client.publishEvent(org.misarch.tax.event.TaxEvents.PUBSUB_NAME, "tax/$topic", message).awaitSingleOrNull()
    }

}