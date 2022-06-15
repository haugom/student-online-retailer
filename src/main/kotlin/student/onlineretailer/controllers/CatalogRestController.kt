package student.onlineretailer.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import student.onlineretailer.Item
import student.onlineretailer.service.CatalogService

@RestController()
class CatalogRestController {

    @Autowired
    lateinit var catalogService: CatalogService

    @Cacheable(
        value=["catalogs"]
    )
    @GetMapping("/catalog")
    fun getAllItems(): List<Item> {
        return catalogService.GetAll()
    }

    @CachePut(
        cacheNames = ["catalogs"],
        key = "#id"
    )
    @GetMapping("/catalog/{id}")
    fun getItem(@PathVariable("id") id: Int): Item {
        if (catalogService.Contains(id) == false) {
            return Item(-1, "", 0.0)
        }
        return catalogService.Get(id)
    }

    @PostMapping("/catalog")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody item: Item): Item {
        return catalogService.Create(item)
    }

    @PutMapping("/catalog/{id}")
    fun put(@PathVariable id: Int, @RequestBody item: Item): ResponseEntity<Item> {
        if (catalogService.Contains(id) == false) {
            return ResponseEntity.notFound().build()
        }
        catalogService.Update(id, item)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/catalog/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Item> {
        if (catalogService.Contains(id) == false) {
            return ResponseEntity.notFound().build()
        }
        catalogService.Delete(id)
        return ResponseEntity.ok().build()
    }
}