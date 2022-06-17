package org.haugom.studentwebflux

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

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
}