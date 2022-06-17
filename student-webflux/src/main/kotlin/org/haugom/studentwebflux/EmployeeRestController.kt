package org.haugom.studentwebflux

import mu.KotlinLogging
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(value = ["/employees"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Profile("controller-enpoint-style")
class EmployeeRestController(@Autowired val service: EmployeeService) {

    @PostConstruct
    fun init() {
        logger.info { "controller created" }
    }

    @GetMapping
    fun getAll(): Publisher<Employee> {
        logger.info { "get all" }
        return service.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long): Publisher<Employee> {
        logger.info { "getById with id $id" }
        return service.getById(id)
    }

    @PostMapping
    fun create(@RequestBody employee: Employee): Publisher<ResponseEntity<Employee>> {
        logger.info { "Creating employee $employee" }
        return this.service
            .create(employee.name, employee.salary, employee.region)
            .map { e ->
                ResponseEntity
                    .created(URI.create("/employees/${e.id}"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .build()
            }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody employee: Employee): Publisher<ResponseEntity<Employee>> {
        logger.info { "update() on $id with $employee" }
        return service
            .update(id, employee.name, employee.salary, employee.region)
            .map {
                ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .build()
            }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): Publisher<Employee> {
        logger.info { "deleteing employee ${id}" }
        return service.delete(id)
    }
}