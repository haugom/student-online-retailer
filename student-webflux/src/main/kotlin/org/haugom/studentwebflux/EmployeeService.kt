package org.haugom.studentwebflux

import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger {}

@Service
class EmployeeService(val repo: EmployeeRepository, pub: ApplicationEventPublisher) {

    fun getAll(): Flux<Employee> {
        return repo.findAll()
    }

    fun getById(id: Long): Mono<Employee> {
        return repo.findById(id)
    }

    fun getNum(): Mono<Long?>? {
        return repo.count()
    }

    fun create(name: String?, salary: Double, region: String?): Mono<Employee> {
        return repo.save(Employee(null, name!!, salary, region!!))
    }

    fun update(id: Long?, name: String?, salary: Double, region: String?): Mono<Employee> {
        return repo.findById(id!!)
            .map { e -> Employee(e.id, name!!, salary, region!!) }
            .flatMap { t -> repo.save(t).thenReturn(t) }
    }

    fun delete(id: Long?): Mono<Employee?>? {
        return repo.findById(id!!)
            .flatMap { e -> repo.deleteById(e.id!!).thenReturn(e) }
    }
}