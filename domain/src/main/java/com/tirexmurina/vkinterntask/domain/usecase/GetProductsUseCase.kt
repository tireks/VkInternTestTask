package com.tirexmurina.vkinterntask.domain.usecase

import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.domain.repository.ProductRepository

interface IGetProductsUseCase{

    suspend operator fun invoke() : List<Product>

}

class GetProductsUseCase(
    private val repository: ProductRepository
) : IGetProductsUseCase{
    override suspend fun invoke(): List<Product> {
        return repository.getUsersTest()
    }

}