package com.tirexmurina.vkinterntask.data.remote.category

import com.tirexmurina.vkinterntask.domain.entity.Category

class CategoryConverter {
    fun convert (from : CategoryModel) : Category =
        Category(
            name = from.name
        )
}