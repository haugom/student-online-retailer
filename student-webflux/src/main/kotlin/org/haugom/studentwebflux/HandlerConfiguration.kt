package org.haugom.studentwebflux

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
@Profile("handler-style-endpoint")
class HandlerConfiguration {
    @Bean
    fun routes(employeeRestHandler: EmployeeRestHandler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(GET("/employees"), employeeRestHandler::getAll)
            .andRoute(GET("/employees/{id}"), employeeRestHandler::getById)
            .andRoute(POST("/employees"), employeeRestHandler::create)
            .andRoute(PUT("/employees/{id}"), employeeRestHandler::update)
            .andRoute(DELETE("/employees/{id}"), employeeRestHandler::delete)
    }
}