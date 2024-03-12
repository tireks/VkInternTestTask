package com.tirexmurina.vkinterntask.domain.repository

import com.tirexmurina.vkinterntask.domain.entity.Category
import com.tirexmurina.vkinterntask.domain.entity.Product

interface ProductRepository {

    suspend fun getProducts(parameters : Map<String, String>) : List<Product>

    suspend fun getProductById(id: String) : Product

    suspend fun getProductsByCategory(category: String, parameters: Map<String, String>) : List<Product>

}