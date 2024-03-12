package com.tirexmurina.vkinterntask.domain.usecase

import com.tirexmurina.vkinterntask.domain.entity.Category
import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.domain.repository.CategoryRepository
import com.tirexmurina.vkinterntask.domain.repository.ProductRepository

interface IGetCategoriesUseCase {

    suspend operator fun invoke() : List<Category>

}

class GetCategoriesUseCase (
    private val repository: CategoryRepository
) : IGetCategoriesUseCase{
    override suspend fun invoke(): List<Category> {
        return repository.getCategories()
    }
}