package com.tirexmurina.vkinterntask.domain.usecase

import com.tirexmurina.vkinterntask.domain.entity.Category
import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.domain.repository.ProductRepository

interface IGetProductsByCategoryUseCase {

    suspend operator fun invoke(category: Category, parameters : Map<String, String>) : List<Product>
}

class GetProductsByCategoryUseCase (
    private val repository: ProductRepository
) : IGetProductsByCategoryUseCase {
    override suspend fun invoke(category: Category, parameters: Map<String, String>): List<Product> {
        return repository.getProductsByCategory(category.name, parameters)
    }

}