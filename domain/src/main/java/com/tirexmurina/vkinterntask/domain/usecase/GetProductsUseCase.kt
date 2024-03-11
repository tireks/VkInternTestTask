package com.tirexmurina.vkinterntask.domain.usecase

import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.domain.repository.ProductRepository

interface IGetProductsUseCase{

    suspend operator fun invoke(parameters : Map<String, String>) : List<Product>

}

class GetProductsUseCase(
    private val repository: ProductRepository
) : IGetProductsUseCase{
    override suspend fun invoke(parameters : Map<String, String>): List<Product> {
        return repository.getProducts(parameters)
    }

}