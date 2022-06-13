package student.onlineretailer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
class ExampleData {

    @Bean("catalog")
    @Scope("application")
    fun catalog(): Map<Int, Item> {
        var sampleData = mutableMapOf(
            1 to Item(1, "Kaffe", 1.0),
            2 to Item(2, "Banan", 0.5)
        )
        return sampleData
    }
}