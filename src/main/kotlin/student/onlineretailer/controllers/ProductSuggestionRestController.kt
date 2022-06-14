package student.onlineretailer.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import student.onlineretailer.data.ProductSuggestion
import student.onlineretailer.repo.ProductSuggestionRepository
import javax.transaction.Transactional

@RestController
class ProductSuggestionRestController {

    @Autowired
    lateinit var productSuggestionRepository: ProductSuggestionRepository

    @PostMapping("/ps")
    @Transactional
    fun ps(@RequestBody ps: ProductSuggestion): ResponseEntity<Long> {
        val id = productSuggestionRepository.insertProductSuggestion(ps)
        return ResponseEntity.ok().body(id)
    }

    @GetMapping("/ps")
    fun list(): ResponseEntity<Collection<ProductSuggestion>> {
        return ResponseEntity.ok(productSuggestionRepository.getProductSuggestions())
    }

    @GetMapping("/ps/{id}")
    fun get(@PathVariable(name= "id") id: Long): ResponseEntity<ProductSuggestion> {
        val productSuggestions = productSuggestionRepository.getProductSuggestions(id)
        if (productSuggestions == null) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(productSuggestions)
    }

    @PutMapping("/ps/{id}/price")
    @Transactional
    fun modifyPrice(
        @PathVariable(name = "id") id: Long,
        @RequestBody ps: ProductSuggestion
    ): ResponseEntity<ProductSuggestion> {
        val modifyPrice = productSuggestionRepository.modifyPrice(id, ps.recommendedPrice)
        if (modifyPrice) {
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.badRequest().build()
    }

    @PutMapping("/ps/{id}/sales")
    @Transactional
    fun modifySales(
        @PathVariable(name = "id") id: Long,
        @RequestBody ps: ProductSuggestion
    ): ResponseEntity<ProductSuggestion> {
        val modifyPrice = productSuggestionRepository.modifySales(id, ps.estimatedAnnualSales)
        if (modifyPrice) {
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.badRequest().build()
    }

    @DeleteMapping("/ps")
    @Transactional
    fun deleteAll() {
        productSuggestionRepository.deleteProductSuggestions()
    }

}