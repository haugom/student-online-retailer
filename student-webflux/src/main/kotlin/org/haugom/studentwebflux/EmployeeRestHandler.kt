package org.haugom.studentwebflux

import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.lang.Long
import java.net.URI
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@Component
@Profile("handler-style-endpoint")
class EmployeeRestHandler(val service: EmployeeService) {
    @PostConstruct
    fun init() {
        logger.info { "controller created" }
    }

    fun getAll(serverRequest: ServerRequest): Mono<ServerResponse> {
        val gotten = service.getAll()
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(gotten, Employee::class.java)
    }

    fun getById(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id = serverRequest.pathVariable("id")
        val gotten = service.getById(Long.parseLong(id))
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(gotten, Employee::class.java)
    }

    fun create(serverRequest: ServerRequest): Mono<ServerResponse> {
        val created = serverRequest.bodyToFlux(Employee::class.java)
            .flatMap { employee -> service.create(employee.name, employee.salary, employee.region) }
        return Mono.from(created)
            .flatMap { emploeyee ->
                ServerResponse
                    .created(URI.create("/employees/${emploeyee.id}"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .build()
            }
    }

    fun update(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id = serverRequest.pathVariable("id")
        val updated = serverRequest.bodyToFlux(Employee::class.java)
            .flatMap { employee -> service.update(Long.parseLong(id), employee.name, employee.salary, employee.region) }
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(updated, Employee::class.java)
    }

    fun delete(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id = serverRequest.pathVariable("id")
        val deleted = service.delete(Long.parseLong(id))
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(deleted, Employee::class.java)
    }

}