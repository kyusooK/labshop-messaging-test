package contracts.messaging
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    // The Identifier which can be used to identify it later.
    label 'OrderPlaced'
    input {
        // Contract will be triggered by the following method.
        triggeredBy('orderPlaced()')
    }
    outputMessage {
        sentTo 'eventTopic'
        // Consumer Expected Payload spec. that a JSON message must have, 
        // If the Producer-side test is OK, then send the following msg to event-out channel.
        body(
            eventType: "'OrderPlaced'",
                id: 1,
                customerId: '1',
                productId: '1',
                productName: 'TV',
                qty: 5,
        )
        bodyMatchers {
            jsonPath('$.id', byRegex(nonEmpty()).asLong())
            jsonPath('$.customerid', byRegex(nonEmpty()).asString())
            jsonPath('$.productid', byRegex(nonEmpty()).asString())
            jsonPath('$.productname', byRegex(nonEmpty()).asString())
            jsonPath('$.qty', byRegex(nonEmpty()).asInteger())
        }
        headers {
            messagingContentType(applicationJson())
        }
    }
}