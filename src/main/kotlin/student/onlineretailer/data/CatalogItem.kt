package student.onlineretailer.data

import javax.persistence.*

@Entity
@Table(name="items")
class CatalogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1
    var description: String = ""
    var price: Double = 0.0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CatalogItem

        if (id != other.id) return false
        if (description != other.description) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }


}