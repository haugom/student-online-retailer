package student.onlineretailer.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import student.onlineretailer.service.CatalogService
import student.onlineretailer.Item

@RestController()
class CatalogRestController {

    @Autowired
    lateinit var catalogService: CatalogService

    @GetMapping("/catalog")
    fun getAllItems(): List<Item> {
        return catalogService.GetAll()
    }

    @GetMapping("/catalog/{id}")
    fun getItem(@PathVariable("id") id: Int): ResponseEntity<Item> {
        if (catalogService.Contains(id) == false) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok().body(catalogService.Get(id))
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