package com.tirexmurina.vkinterntask.domain.usecase

import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.domain.repository.ProductRepository

interface IGetProductByIdUseCase{

    suspend operator fun invoke(id: String) : Product

}

class GetProductByIdUseCase (
    private val repository: ProductRepository
) : IGetProductByIdUseCase {
    override suspend fun invoke(id: String): Product {
        return repository.getProductById(id)
    }
}