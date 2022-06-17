package org.haugom.studentwebflux

import base.EmployeeRestBaseTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("controller-enpoint-style")
@Import(EmployeeRestController::class,EmployeeService::class)
class RestControllerTest: EmployeeRestBaseTest() {
}