package com.tirexmurina.vkinterntask.presentation

import com.tirexmurina.vkinterntask.domain.entity.Category
import com.tirexmurina.vkinterntask.domain.entity.Product

sealed class HomeViewState {
    data object Initial : HomeViewState()
    data object Loading : HomeViewState()
    data class Content (
        val items: List<Product>,
        val expandAvailable : Boolean,
        val categories : List<Category>,
        val activeCategory: Category?
    ): HomeViewState()
    data class Error(val errorMsg: String) : HomeViewState()
}