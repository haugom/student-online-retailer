package org.haugom.studentwebflux

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("employees")
data class Employee(
    @Id
    var id: Long?,
    val name: String,
    val salary: Double,
    val region: String
)