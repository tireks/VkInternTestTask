package com.tirexmurina.vkinterntask.data.remote

import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProductRepositoryImpl (
    private val service: ProductAPI,
    private val converter: ProductConverter
): ProductRepository {

    override suspend fun getUsersTest(): List<Product> =
        service.getTest().products.map { converter.convert(it) }

}