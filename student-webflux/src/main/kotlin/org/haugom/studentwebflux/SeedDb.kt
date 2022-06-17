package org.haugom.studentwebflux

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

private val logger = KotlinLogging.logger {}

@Component
class SeedDb : ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    lateinit var employeeRepository: PersonRepository

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        logger.info { "onApplicationEvent" }

        employeeRepository.deleteAll()
            .thenMany(Flux.just(
                Person(null, "john", 100.0, "Stavanger"),
                Person(null, "peter", 112.0, "Oslo"),
                Person(null, "jack", 85.0, "Drammen")
            )
                .flatMap { e -> employeeRepository.save(e) })
            .thenMany(employeeRepository.findAll())
            .subscribe { e -> logger.info { "inserted $e" } }

    }
}