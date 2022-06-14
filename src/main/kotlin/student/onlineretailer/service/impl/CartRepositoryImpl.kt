package student.onlineretailer.service.impl

import org.springframework.stereotype.Component
import student.onlineretailer.service.CartRepository
import java.util.*

@Component
class CartRepositoryImpl: CartRepository {

    var cart: MutableMap<Int, Int> = mutableMapOf()

    override fun add(itemId: Int, quantity: Int) {
        var currentQuantity = quantity
        if (this.cart.containsKey(itemId)) {
            currentQuantity = this.cart.get(itemId)!! + quantity
        }
        this.cart.put(itemId, currentQuantity)
    }

    override fun remove(itemId: Int) {
        this.cart.remove(itemId)
    }

    override fun getAll(): MutableMap<Int, Int> {
        return Collections.unmodifiableMap(this.cart)
    }
}