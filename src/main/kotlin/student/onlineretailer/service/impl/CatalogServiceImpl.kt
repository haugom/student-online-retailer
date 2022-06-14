package student.onlineretailer.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import student.onlineretailer.Item
import student.onlineretailer.data.CatalogItem
import student.onlineretailer.repo.CatalogItemRepository
import student.onlineretailer.service.CatalogService
import javax.annotation.PostConstruct

@Component
class CatalogServiceImpl: CatalogService {

    @Autowired
    lateinit var catalogItemRepository: CatalogItemRepository

    @Autowired
    lateinit var originalData: Map<Int, Item>

    @PostConstruct
    fun init() {
        originalData.forEach {
            val catalogItem = CatalogItem()
            val originalItem = it.value
            catalogItem.id = originalItem.id.toLong()
            catalogItem.description = originalItem.description
            catalogItem.price = originalItem.price
            catalogItemRepository.save(catalogItem)
        }
    }

    override fun GetAll(): List<Item> {
        var copy: ArrayList<Item> = ArrayList()
        catalogItemRepository.findAll().forEach {
            copy.add(Item(it.id.toInt(),it.description,it.price))
        }
        return copy
    }

    override fun Contains(id: Int): Boolean {
        return catalogItemRepository.existsById(id.toLong())
    }

    override fun Get(id: Int): Item {
        val result = catalogItemRepository.findById(id.toLong())
        val foundItem = result.get()
        return Item(foundItem.id.toInt(), foundItem.description, foundItem.price)
    }

    override fun Create(item: Item): Item {
        var newItem = CatalogItem()
        newItem.description = item.description
        newItem.price = item.price
        val savedItem = catalogItemRepository.save(newItem)
        return Item(savedItem.id.toInt(), savedItem.description, savedItem.price)
    }

    override fun Update(id: Int, item: Item) {
        var updatedItem = CatalogItem()
        updatedItem.id = id.toLong()
        updatedItem.description = item.description
        updatedItem.price = item.price
        catalogItemRepository.save(updatedItem)
    }

    override fun Delete(id: Int) {
        catalogItemRepository.deleteById(id.toLong())
    }
}