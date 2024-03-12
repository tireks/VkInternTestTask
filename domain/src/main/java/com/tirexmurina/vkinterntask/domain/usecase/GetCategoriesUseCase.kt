package com.tirexmurina.vkinterntask.domain.usecase

import com.tirexmurina.vkinterntask.domain.entity.Category
import com.tirexmurina.vkinterntask.domain.repository.CategoryRepository

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