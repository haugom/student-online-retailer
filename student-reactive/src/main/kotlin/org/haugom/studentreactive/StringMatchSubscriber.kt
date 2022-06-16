package org.haugom.studentreactive

import mu.KotlinLogging
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Flow

private val logger = KotlinLogging.logger {}

class StringMatchSubscriber(): Flow.Subscriber<String> {

    lateinit var resultFactory: ResultFactory
    lateinit var term: String
    lateinit var url: String
    var found: Boolean = false
    val completableFuture = CompletableFuture<Result>()
    lateinit var subscription: Flow.Subscription

    constructor(url: String, term: String, resultFactory: ResultFactory): this() {
        this.url = url
        this.term = term
        this.resultFactory = resultFactory
    }

    override fun onSubscribe(subscription: Flow.Subscription) {
        this.subscription = subscription
        subscription.request(1)
    }

    override fun onError(throwable: Throwable?) {
        logger.info { "error occured: $throwable" }
        completableFuture.complete(resultFactory.Exceptionally(url, term, java.lang.Exception(throwable)))
    }

    override fun onComplete() {
        logger.info { "onComplete: found: $found" }
        if (!found) {
            completableFuture.complete(resultFactory.Normal(url, term, false))
        }
    }

    override fun onNext(item: String) {
//        logger.info { "onNext: item: $item" }
        if (item.contains(term, true)) {
            found = true
            logger.info { "found the item, cancelling" }
            completableFuture.complete(resultFactory.Normal(url, term, true))
            subscription.cancel()
            return
        }
        subscription.request(1)
    }
}