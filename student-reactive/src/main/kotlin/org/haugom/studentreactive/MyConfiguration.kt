package org.haugom.studentreactive

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.http.HttpClient

@Configuration
class MyConfiguration {
    @Bean
    fun createHttpClient(): HttpClient {
        return HttpClient.newHttpClient()
    }
}