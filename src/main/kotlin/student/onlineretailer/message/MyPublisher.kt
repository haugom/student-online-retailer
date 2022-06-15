package student.onlineretailer.message

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class MyPublisher {

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, String>

    fun sendMessage(key: String, value: String) {
        kafkaTemplate.send("HELLO", key, value)
    }
}