package com.tirexmurina.vkinterntask.data.remote.category

import com.tirexmurina.vkinterntask.data.remote.DummyjsonAPI
import com.tirexmurina.vkinterntask.data.remote.product.ProductConverter
import com.tirexmurina.vkinterntask.domain.entity.Category
import com.tirexmurina.vkinterntask.domain.repository.CategoryRepository
import com.tirexmurina.vkinterntask.domain.repository.ProductRepository

class CategoryRepositoryImpl(
    private val service: DummyjsonAPI,
    private val converter: CategoryConverter
) : CategoryRepository{

    override suspend fun getCategories(): List<Category> =
        service.getCategories().map { converter.convert(it) }


}