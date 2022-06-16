package org.haugom.studentreactive

import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class ResultFactoryImpl : ResultFactory {

    override fun Normal(url: String, term: String, found: Boolean): Result {
        val result = Result(url, term, found)
        logger.info { "Search result OK: $result" }
        return result
    }

    override fun Exceptionally(url: String, term: String, exception: Exception): Result {
        val result = Result(url, term, false)
        logger.error { "Search result failed: $result with: $exception" }
        return result
    }
}