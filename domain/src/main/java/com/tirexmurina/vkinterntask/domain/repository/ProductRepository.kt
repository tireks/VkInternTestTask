package com.tirexmurina.vkinterntask.domain.repository

import com.tirexmurina.vkinterntask.domain.entity.Product

interface ProductRepository {

    suspend fun getProducts(parameters : Map<String, String>) : List<Product>

}