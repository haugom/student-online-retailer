package student.onlineretailer.repo

import org.springframework.data.repository.CrudRepository
import student.onlineretailer.data.CatalogItem

interface CatalogItemRepository: CrudRepository<CatalogItem, Long> {
}