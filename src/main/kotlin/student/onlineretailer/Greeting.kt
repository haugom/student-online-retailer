package student.onlineretailer

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel

class Greeting : RepresentationModel<Greeting> {
    val content: String

    @JsonCreator
    constructor(@JsonProperty("content") content: String) : super() {
        this.content = content
    }

}