package org.haugom.studentreactive

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val logger = KotlinLogging.logger {}

@Component("async")
class PageSearcherAsync : PageSearcher {

    @Autowired
    lateinit var resultFactory: ResultFactory

    @Autowired
    lateinit var httpClient: HttpClient

    override fun searchPageFor(url: String, term: String): Result {
        logger.info { "async searching $url..." }
        val uri = URI(url)
        val request = HttpRequest.newBuilder()
            .GET()
            .uri(uri)
            .build()

        try {
            val stringHandler = HttpResponse.BodyHandlers.ofString()
            return httpClient.sendAsync(request, stringHandler)
                .thenApply {httpResponse ->
                    val body = httpResponse.body()
                    val found: Boolean = body.contains(term, true)
                    return@thenApply resultFactory.Normal(url, term, found)
                }.get()
        } catch (e: Exception) {
            return resultFactory.Exceptionally(url, term, e)
        }
    }
}