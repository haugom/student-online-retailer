package student.onlineretailer.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import student.onlineretailer.service.CartService
import student.onlineretailer.Item

@RestController
class CartRestController {
    @Autowired
    @Qualifier("catalog")

    lateinit var catalog: MutableMap<Int, Item>

    @Autowired
    lateinit var cartService: CartService

    @ModelAttribute("varer")
    fun populateVarer(): List<Item> {
        val list: ArrayList<Item> = ArrayList()
        catalog.forEach{
            list.add(it.value)
        }
        return list
    }

    @GetMapping("/items")
    fun getAllItems(): List<Item> {
        val allItemsInCart = cartService.allItemsInCart
        val list: ArrayList<Item> = ArrayList()
        allItemsInCart.forEach {
            val element = catalog.get(it.key)!!
            list.add(element)
        }
        return list
    }

    @GetMapping("cartCost")
    fun getCartCost(): Double {
        return cartService.calculateCartCost()
    }

    @GetMapping("/quantityForItem/{itemId}")
    fun getQuantityForItem(@PathVariable(name = "itemId") itemId: Int): Int {
        val allItemsInCart = cartService.allItemsInCart
        var count = 0
        if (allItemsInCart.containsKey(itemId)) {
            allItemsInCart.forEach {
                if (it.key == itemId) {
                    count = it.value
                }
            }
        }
        return count
    }

    @GetMapping("/varer")
    fun getVarer(@RequestParam(value="index", required = false, defaultValue = "0") index: Int, model: ModelMap): Item {
        val any = model["varer"] as List<Item>
        var currentItem = index
        if (index < 0 || index >= any.size) {
            currentItem = 0
        }
        return any.get(currentItem)
    }
}