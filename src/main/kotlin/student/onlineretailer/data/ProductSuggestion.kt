package student.onlineretailer.data

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class ProductSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1

    var description: String = ""
    var recommendedPrice: Double = 0.0
    var estimatedAnnualSales: Long = 0

    constructor()

    constructor(description: String, recommendedPrice: Double, estimatedAnnualSales: Long) {
        this.description = description
        this.recommendedPrice = recommendedPrice
        this.estimatedAnnualSales = estimatedAnnualSales
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductSuggestion

        if (id != other.id) return false
        if (description != other.description) return false
        if (recommendedPrice != other.recommendedPrice) return false
        if (estimatedAnnualSales != other.estimatedAnnualSales) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + recommendedPrice.hashCode()
        result = 31 * result + estimatedAnnualSales.hashCode()
        return result
    }


}