package student.onlineretailer.controllers

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import student.onlineretailer.Greeting
import student.onlineretailer.message.Receiver

@RestController
class GreetingController {

    @Autowired
    lateinit var receiver: Receiver
    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    @GetMapping("/greetings", produces = ["application/json"])

    fun greeting(@RequestParam(value = "name", required = false, defaultValue = "") name: String): ResponseEntity<Greeting> {
        var greeting = Greeting("Hello $name")
        greeting.add(linkTo(methodOn(GreetingController::class.java).greeting(name)).withSelfRel().withType("GET"))
        rabbitTemplate.convertAndSend("spring-boot-exchange", "foo.bar.baz", "Hello from $name")
        return ResponseEntity.ok().body(greeting)
    }
}