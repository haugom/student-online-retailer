package student.onlineretailer.message

import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class Receiver {
    var latch = CountDownLatch(1)

    fun receiveMessage(message: String) {
        System.out.println("Received <$message>")
        latch.countDown()
    }
}