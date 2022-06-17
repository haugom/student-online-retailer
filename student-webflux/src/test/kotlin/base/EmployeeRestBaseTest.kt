package base

import org.haugom.studentwebflux.Employee
import org.haugom.studentwebflux.EmployeeRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

@WebFluxTest
open class EmployeeRestBaseTest {

    @MockBean
    lateinit var employeeRepository: EmployeeRepository
    @Autowired
    lateinit var client: WebTestClient

    @Test
    fun getAll() {
        Mockito.`when`(employeeRepository.findAll())
            .thenReturn(
                Flux.just(
                    Employee(1, "boba", 1.0, "Tatooine"),
                    Employee(2, "mando", 1.5, "kashyyyk")
                )
            )
        client
            .get()
            .uri("/employees")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[0].id").isEqualTo(1)
            .jsonPath("$.[0].name").isEqualTo("boba")
            .jsonPath("$.[1].id").isEqualTo(2)
            .jsonPath("$.[1].name").isEqualTo("mando")
    }

}