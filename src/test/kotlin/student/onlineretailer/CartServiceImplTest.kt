package student.onlineretailer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import student.onlineretailer.service.CartService

@SpringBootTest
@ActiveProfiles("development")
internal class CartServiceImplTest {

    @Autowired
    lateinit var cartService: CartService

    @Autowired
    lateinit var resourceBean: ResourceBean

    @Test
    fun test() {
        cartService.addItemToCart(1, 2)
        cartService.addItemToCart(2, 10)
        val allItemsInCart = cartService.allItemsInCart
        val kaffe = allItemsInCart.get(1)
        val banan = allItemsInCart.get(2)
        assertEquals(2, kaffe, "antall kaffe")
        assertEquals(10, banan, "antall kaffe")
        val calculateCartCost = cartService.calculateCartCost()
        val estimated: Double = 2 * 1.0 + 10 * 0.5
        assertEquals(estimated, calculateCartCost, "kalkulert pris")
    }

    @Test
    fun testEmail() {
        val contactEmail = cartService.contactEmail
        assertEquals("contact@acme.retail.com", contactEmail, "epost bør være lik")
    }

    @Test
    fun testTax() {
        val salesTaxRate = cartService.salesTaxRate
        assertEquals(0.20, salesTaxRate, "tax bør være lik")
    }

    @Test
    fun testProfile() {
        val getDb = resourceBean.GetDb()
        System.out.println(getDb)
    }
}