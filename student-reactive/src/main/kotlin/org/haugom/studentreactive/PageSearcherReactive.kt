package org.haugom.studentreactive

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException

private val logger = KotlinLogging.logger {}

@Component("reactive")
class PageSearcherReactive : PageSearcher {
    @Autowired
    lateinit var resultFactory: ResultFactory

    @Autowired
    lateinit var httpClient: HttpClient

    override fun searchPageFor(url: String, term: String): Result {
        val stringMatchSubscriber = StringMatchSubscriber(url, term, resultFactory)
        logger.info { "async searching $url..." }
        val uri = URI(url)
        val request = HttpRequest.newBuilder()
            .GET()
            .uri(uri)
            .build()

        return try {
            val stringHandler = HttpResponse.BodyHandlers.fromLineSubscriber(stringMatchSubscriber)
            httpClient.sendAsync(request, stringHandler)
            stringMatchSubscriber.completableFuture.get()
        } catch (e: CancellationException) {
            logger.info { "request was cancelled, ignoring" }
            return stringMatchSubscriber.completableFuture.get()
        } catch (e: Exception) {
            logger.info { "exception occured" }
            resultFactory.Exceptionally(url, term, e)
        }
    }
}