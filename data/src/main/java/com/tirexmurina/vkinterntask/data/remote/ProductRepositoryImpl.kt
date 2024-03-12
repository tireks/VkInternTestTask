package com.tirexmurina.vkinterntask.data.remote

import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProductRepositoryImpl (
    private val service: ProductAPI,
    private val converter: ProductConverter
): ProductRepository {

    override suspend fun getProducts(parameters: Map<String, String>): List<Product> =
        service.getProducts(parameters).products.map { converter.convert(it) }

    override suspend fun getProductById(id: String): Product =
        converter.convert(service.getProductById("products/$id"))


}