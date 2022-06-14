package student.onlineretailer.controllers

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import student.onlineretailer.Greeting

@RestController
class GreetingController {
    @GetMapping("/greetings", produces = ["application/json"])

    fun greeting(@RequestParam(value = "name", required = false, defaultValue = "") name: String): ResponseEntity<Greeting> {
        var greeting = Greeting("Hello $name")
        greeting.add(linkTo(methodOn(GreetingController::class.java).greeting(name)).withSelfRel().withType("GET"))
        return ResponseEntity.ok().body(greeting)
    }
}