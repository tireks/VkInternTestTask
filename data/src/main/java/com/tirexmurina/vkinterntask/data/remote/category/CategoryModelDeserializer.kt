package com.tirexmurina.vkinterntask.data.remote.category

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CategoryModelDeserializer : JsonDeserializer<CategoryModel> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CategoryModel {
        val categoryName = json?.asString ?: ""
        return CategoryModel(categoryName)
    }
}

