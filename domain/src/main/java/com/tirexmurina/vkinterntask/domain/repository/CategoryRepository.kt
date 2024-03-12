package com.tirexmurina.vkinterntask.domain.repository

import com.tirexmurina.vkinterntask.domain.entity.Category

interface CategoryRepository {

    suspend fun getCategories() : List<Category>

}