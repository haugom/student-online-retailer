package org.haugom.studentwebflux

import base.EmployeeRestBaseTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("handler-style-endpoint")
@Import(EmployeeRestHandler::class,HandlerConfiguration::class,EmployeeService::class)
class RestHandlerTest: EmployeeRestBaseTest() {
}