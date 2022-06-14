package student.onlineretailer.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import student.onlineretailer.Item
import student.onlineretailer.service.CatalogService
import javax.annotation.PostConstruct

@Component
class CatalogServiceImpl: CatalogService {

    @Autowired
    lateinit var originalData: Map<Int, Item>

    var currentData: MutableMap<Int, Item> = mutableMapOf()
    var counter = 0

    @PostConstruct
    fun init() {
        originalData.forEach {
            currentData.put(it.key, it.value)
            counter++
        }
        counter++
    }

    override fun GetAll(): List<Item> {
        var copy: ArrayList<Item> = ArrayList()
        currentData.forEach {
            copy.add(it.value)
        }
        return copy
    }

    override fun Contains(id: Int): Boolean {
        return currentData.containsKey(id)
    }

    override fun Get(id: Int): Item {
        return currentData.get(id)!!
    }

    override fun Create(item: Item): Item {
        var newItem = Item(counter++, item.description, item.price)
        currentData.put(newItem.id, newItem)
        return newItem
    }

    override fun Update(id: Int, item: Item) {
        currentData.put(id, item)
    }

    override fun Delete(id: Int) {
        currentData.remove(id)
    }
}