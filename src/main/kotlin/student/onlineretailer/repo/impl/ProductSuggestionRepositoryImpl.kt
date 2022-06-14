package student.onlineretailer.repo.impl

import org.springframework.stereotype.Component
import student.onlineretailer.data.ProductSuggestion
import student.onlineretailer.repo.ProductSuggestionRepository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery

@Component
class ProductSuggestionRepositoryImpl : ProductSuggestionRepository {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun getProductSuggestions(): Collection<ProductSuggestion> {
        var query: TypedQuery<ProductSuggestion> = entityManager.createQuery("select e from ProductSuggestion e", ProductSuggestion::class.java)
        return query.resultList
    }

    override fun getProductSuggestions(id: Long): ProductSuggestion? {
        return entityManager.find(ProductSuggestion::class.java, id)
    }

    override fun insertProductSuggestion(ps: ProductSuggestion): Long {
        entityManager.persist(ps)
        entityManager.flush()
        return ps.id
    }

    override fun modifyPrice(id: Long, newPrice: Double): Boolean {
        val find = entityManager.find(ProductSuggestion::class.java, id)
        if (find != null) {
            find.recommendedPrice = newPrice
            return true
        }
        return false
    }

    override fun modifySales(id: Long, newSales: Long): Boolean {
        val find = entityManager.find(ProductSuggestion::class.java, id)
        if (find != null) {
            find.estimatedAnnualSales = newSales
            return true
        }
        return false
    }

    override fun deleteProductSuggestions() {
        getProductSuggestions().forEach {
            entityManager.remove(it)
        }
        entityManager.flush()
    }
}