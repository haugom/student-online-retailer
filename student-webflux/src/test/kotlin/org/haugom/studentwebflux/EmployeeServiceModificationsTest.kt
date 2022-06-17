package org.haugom.studentwebflux

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import org.springframework.util.Assert
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

private val logger = KotlinLogging.logger {}

@DataR2dbcTest
@Import(EmployeeService::class)
class EmployeeServiceModificationsTest(
    @Autowired val service: EmployeeService,
    @Autowired val repo: EmployeeRepository
) {

    @Test
    fun `create employee returns created employee with id`() {
        val createdEmployee: Mono<Employee> = service.create("andrew", 7000.0, "London")
        StepVerifier
            .create(createdEmployee)
            .expectNextMatches { emp -> emp.id != null}
            .verifyComplete()
    }

    @Test
    fun `updateEmployee employee should be updated`() {
        val updatedEmployee = service.create("simon the sorcere", 8000.0, "Wonderland")
            .flatMap { emp -> service.update(emp.id, "simon the new sorcerer", 8000.0, "Wonderland") }
        StepVerifier
            .create(updatedEmployee)
            .expectNextMatches { emp -> emp.name == "simon the new sorcerer" }
            .verifyComplete()
    }

    @Test
    fun `deleteEmployee the employee should be deleted from the database`() {
        var id: Long = 0
        // insert one user
        service.create("test", 1.0, "test").subscribe()

        val abrahamsen = Employee(null, "abrahamsen", 2.0, "Oslo")

        // insert another user and delete it
        val deletedEmployee = service.create(abrahamsen.name, abrahamsen.salary, abrahamsen.region)
            .flatMap { emp ->
                id = emp.id!!
                service.delete(emp.id)
            }
        StepVerifier
            .create(deletedEmployee)
            .expectNextMatches { emp -> emp!!.name == abrahamsen.name }
            .verifyComplete()

        logger.info { "id was $id" }
        abrahamsen.id = id

        val byId = service.getById(id)
        StepVerifier
            .create(byId)
            .expectComplete()

        val liste: ArrayList<Employee> = ArrayList()
        service.getAll().subscribe { emp ->
            liste.add(emp)
            logger.info { "reading $emp" }
        }

        Assert.isTrue(liste.contains(abrahamsen) == false, "it should only be one employee")
    }
}