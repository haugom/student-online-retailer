package student.onlineretailer.service

import student.onlineretailer.Item

interface CatalogService {
    fun GetAll(): List<Item>
    fun Contains(id: Int): Boolean
    fun Get(id: Int): Item
    fun Create(item: Item): Item
    fun Update(id: Int, item: Item)
    fun Delete(id: Int)
}