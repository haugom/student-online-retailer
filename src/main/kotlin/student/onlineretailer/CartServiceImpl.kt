package student.onlineretailer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
@org.springframework.context.annotation.Lazy
class CartServiceImpl: CartService {

    @Value("\${onlineRetailer.salesTaxRate}")
    var cSalesTaxRate: Double = 0.0

    @Value("\${onlineRetailer.deliveryCharge.normal}")
    private val standardDeliveryCharge = 0.0

    @Value("\${onlineRetailer.deliveryCharge.threshold}")
    private val deliveryChargeThreshold = 0.0

    @Value("\${contactEmail}")
    lateinit var corpContactEmail: String

    @PostConstruct
    fun init() {

    }

    @Autowired
    @Qualifier("catalog")
    lateinit var catalog: MutableMap<Int, Item>

    @Autowired
    lateinit var cartRepository: CartRepository

    override fun addItemToCart(id: Int, quantity: Int) {
        if (this.catalog.containsKey(id)) {
            this.cartRepository.add(id, quantity)
        }
    }

    override fun removeItemFromCart(id: Int) {
        this.cartRepository.remove(id)
    }

    override fun getAllItemsInCart(): MutableMap<Int, Int> {
        return this.cartRepository.all
    }

    override fun calculateCartCost(): Double {
        var cost = 0.0
        this.cartRepository.all.forEach {
            cost += (this.catalog.get(it.key!!)?.price ?: 0.0) * it.value
        }
        return cost
    }

    override fun getContactEmail(): String {
        return this.corpContactEmail
    }

    override fun getSalesTaxRate(): Double {
        return this.cSalesTaxRate
    }

    override fun calculateSalesTax(): Double {
        return cSalesTaxRate * calculateCartCost()
    }

    override fun calculateDeliveryCharge(): Double {
        var totalCost = calculateCartCost()
        if ((totalCost == 0.0) || (totalCost >= deliveryChargeThreshold)) {
            return 0.0
        } else {
            return standardDeliveryCharge
        }
    }
}