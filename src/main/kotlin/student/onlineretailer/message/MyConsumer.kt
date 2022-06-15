package student.onlineretailer.message

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class MyConsumer {
    @KafkaListener(topics = ["HELLO"], groupId = "demo")
    fun receiveMessage(
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) key: String,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String,
        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) timestamp: String,
        value: String
    ) {
       System.out.println("$timestamp\tkey: <$key> topic: <$topic> value: <$value>")
    }
}