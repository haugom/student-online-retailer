package student.onlineretailer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class Hello {

    @Autowired
    lateinit var cardService: CartRepository

    @GetMapping("/greeting")
    fun greeting(@RequestParam(name = "name", required = false, defaultValue = "world") name: String, model: Model): String {
        val allItemsInCart = this.cardService.all
        model.addAttribute("allItems", allItemsInCart)
        return "greeting"
    }

}