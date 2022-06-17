package org.haugom.studentwebflux

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("person")
data class Person(
    @Id
    val id: Long?,
    val name: String,
    val salary: Double,
    val region: String
)