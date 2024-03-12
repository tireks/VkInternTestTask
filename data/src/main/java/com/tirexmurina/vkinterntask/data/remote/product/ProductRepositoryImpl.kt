package com.tirexmurina.vkinterntask.data.remote.product

import com.tirexmurina.vkinterntask.data.remote.DummyjsonAPI
import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.domain.repository.ProductRepository

class ProductRepositoryImpl (
    private val service: DummyjsonAPI,
    private val converter: ProductConverter
): ProductRepository {

    override suspend fun getProducts(parameters: Map<String, String>): List<Product> =
        service.getProducts(parameters).products.map { converter.convert(it) }

    override suspend fun getProductById(id: String): Product =
        converter.convert(service.getProductById("products/$id"))

    override suspend fun getProductsByCategory(category: String, parameters: Map<String, String>): List<Product> =
        service.getProductsByCategory("products/category/$category", parameters).products.map { converter.convert(it) }



}