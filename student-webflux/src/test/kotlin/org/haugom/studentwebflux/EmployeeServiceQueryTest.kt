package org.haugom.studentwebflux

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.function.Predicate

private val logger = KotlinLogging.logger {}

@DataR2dbcTest
@Import(EmployeeService::class)
class EmployeeServiceQueryTest(
    @Autowired val service: EmployeeService,
    @Autowired val repo: EmployeeRepository
) {

    @Test()
    fun `getAllEmployees returns all employees`() {
        val toSave = Flux.just(
            Employee(null, "Peter", 1.0, "Oslo"),
            Employee(null, "Luke", 2.0, "Tatooine"),
            Employee(null, "Yoda", 3.0, "Dagobah"),
            Employee(null, "Boba", 8.0, "Tatooine")
        )

        val deleteAllEmployees = repo.deleteAll()
        StepVerifier
            .create(deleteAllEmployees)
            .verifyComplete()

        val savedEmployees = repo.saveAll(toSave)
        StepVerifier
            .create(savedEmployees)
            .expectNextCount(4)
            .verifyComplete()

        savedEmployees.subscribe { emp ->
            logger.info { "Saved employee $emp" }
        }

        val gottenEmployees = service.getAll()
        val testEmployee =
            Predicate { gemp: Employee ->
                toSave.any { semp: Employee ->
                    System.out.println("GOTTEN: " + gemp.id + " " + gemp.name + " " + gemp.salary + " " + gemp.region)
                    System.out.println("SAVED:  " + semp.id + " " + semp.name + " " + semp.salary + " " + semp.region)
                    System.out.println(
                        """
                              ${"RESULT: " + semp.equals(gemp)}
                              
                              """.trimIndent()
                    )
                    semp.equals(gemp)
                }.block()!!
            }

        StepVerifier
            .create(gottenEmployees)
            .expectNextMatches(testEmployee)
            .expectNextMatches(testEmployee)
            .expectNextMatches(testEmployee)
            .expectNextMatches(testEmployee)
            .verifyComplete()

    }
}