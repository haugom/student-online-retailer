package org.haugom.studentwebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
class StudentWebfluxApplication

fun main(args: Array<String>) {
    runApplication<StudentWebfluxApplication>(*args)
}
