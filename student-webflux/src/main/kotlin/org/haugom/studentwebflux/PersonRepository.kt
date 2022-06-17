package org.haugom.studentwebflux

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface PersonRepository: ReactiveCrudRepository<Person, Long> {
    @Query("SELECT COUNT(*) FROM EMPLOYEES WHERE REGION = :region")
    fun countByRegion(region: String): Int

    @Query("SELECT * FROM EMPLOYEES WHERE REGION = :region")
    fun findByRegion(region: String): List<Person>
}