package student.onlineretailer.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import student.onlineretailer.message.MyPublisher

@RestController
@RequestMapping("/mykafka")
class MyRestController {
    @Autowired
    lateinit var myPublisher: MyPublisher

    @GetMapping("/publish")
    fun sendMessageToKafkaTopic(@RequestParam("value") value: String): List<String> {
        val messages = ArrayList<String>()
        for (i in 1..5) {
            val key = "key$i"
            myPublisher.sendMessage(key, value)
            messages.add("Published $key:$value\n")
        }
        return messages
    }
}