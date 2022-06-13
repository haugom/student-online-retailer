package student.onlineretailer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Update {

    @Autowired
    lateinit var cardService: CartRepository

    @PostMapping("/add")
    fun add(@RequestBody item: Item): String {
        this.cardService.add(item.id, 1)
        return ""
    }

    @PostMapping("/delete")
    fun delete(@RequestBody item: Item): String {
        this.cardService.remove(item.id)
        return ""
    }

}