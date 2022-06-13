package student.onlineretailer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class CartController {
    @Autowired
    lateinit var cartService: CartService
    @Autowired
    @Qualifier("catalog")
    lateinit var catalog: MutableMap<Int, Item>

    @RequestMapping("/")
    fun showCatalog(): String {
        return "catalog"
    }

    @RequestMapping("/addItemToCart")
    fun addItemToCart(model: Model, @RequestParam("id") id: Int, @RequestParam("quantity") quantity: Int): String {
        if (id != null && quantity != null) {
            cartService.addItemToCart(id, quantity)
            model.addAttribute(
                "message",
                java.lang.String.format("Added to cart: %s [x%d]", catalog[id]!!.description, quantity)
            )
        }
        return "catalog"
    }

    @RequestMapping("showCart")
    fun showCart(model: Model): String {
        model.addAttribute("cart", cartService.allItemsInCart)
        model.addAttribute("cartCost", String.format("£%.2f", cartService.calculateCartCost()))
        model.addAttribute("salesTax", String.format("£%.2f", cartService.calculateSalesTax()))
        model.addAttribute("deliveryCharge", String.format("£%.2f", cartService.calculateDeliveryCharge()))
        return "cart"
    }

    @RequestMapping("/removeItemFromCart")
    fun remoteItemFromCart(model: Model, @RequestParam("id") id: Int): String {
        if (id != null) {
            cartService.removeItemFromCart(id)
        }
        return showCart(model)
    }

}