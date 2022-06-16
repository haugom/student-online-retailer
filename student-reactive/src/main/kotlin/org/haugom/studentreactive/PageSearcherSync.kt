package org.haugom.studentreactive

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

private val logger = KotlinLogging.logger {}

@Component("sync")
class PageSearcherSync : PageSearcher {

    @Autowired
    lateinit var resultFactory: ResultFactory

    @Autowired
    lateinit var httpClient: HttpClient

    override fun searchPageFor(url: String, term: String): Result {
        logger.info { "sync searching $url..." }
        val uri = URI(url)
        val request = HttpRequest.newBuilder()
            .GET()
            .uri(uri)
            .build()

        try {
            val stringHandler = BodyHandlers.ofString()
            val httpResponse = httpClient.send(request, stringHandler)
            val body = httpResponse.body()
//            logger.info { body }
            val found: Boolean = body.contains(term, true)
            return resultFactory.Normal(url, term, found)
        } catch (e: Exception) {
            return resultFactory.Exceptionally(url, term, e)
        }
    }
}